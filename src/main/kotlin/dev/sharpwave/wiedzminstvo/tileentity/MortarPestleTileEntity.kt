package dev.sharpwave.wiedzminstvo.tileentity

import dev.sharpwave.wiedzminstvo.registry.RecipeRegistry
import dev.sharpwave.wiedzminstvo.registry.TileEntityRegistry
import dev.sharpwave.wiedzminstvo.utils.RecipeHelper
import dev.sharpwave.wiedzminstvo.utils.WorldHelper
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.minecraft.block.BlockState
import net.minecraft.inventory.IRecipeHelperPopulator
import net.minecraft.inventory.IRecipeHolder
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.AbstractCookingRecipe
import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.RecipeItemHelper
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SUpdateTileEntityPacket
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.INameable
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent

// TODO: Debug why on load there is not rendered item on client side even though on server side it exists
class MortarPestleTileEntity : TileEntity(TileEntityRegistry.PESTLE), INameable, IRecipeHolder, IRecipeHelperPopulator,
    ITickableTileEntity {

    private var name: ITextComponent? = null
    var time = 0
        private set
    var isGrinding = false
    var items: ItemStack = ItemStack.EMPTY
        private set
    var progress: Int = 0
        private set
    var totalGrindCount: Int = 0
        private set
    private val recipesUsed = Object2IntOpenHashMap<ResourceLocation>()
    val canGrind: Boolean
        get() = !level!!.isClientSide and !items.isEmpty and RecipeHelper.canCraftFromItem(
            RecipeRegistry.GRINDING,
            items,
            level!!
        )

    override fun save(tag: CompoundNBT): CompoundNBT {
        super.save(tag)
        return saveToTag(tag)
    }

    override fun load(state: BlockState, tag: CompoundNBT) {
        super.load(state, tag)
        loadFromTag(tag)
    }

    private fun loadFromTag(tag: CompoundNBT) {
        if (tag.contains("CustomName", 8))
            name = ITextComponent.Serializer.fromJson(tag.getString("CustomName"))
        if (tag.contains("Items"))
            items = ItemStack.of(tag.getCompound("Items"))
        if (tag.contains("IsGrinding"))
            isGrinding = tag.getBoolean("IsGrinding")
        if (tag.contains("Progress"))
            progress = tag.getInt("Progress")
        if (tag.contains("TotalGrindCount"))
            totalGrindCount = tag.getInt("TotalGrindCount")
    }

    private fun saveToTag(tag: CompoundNBT): CompoundNBT {
        if (hasCustomName())
            tag.putString("CustomName", ITextComponent.Serializer.toJson(name!!))

        val itemsTag = CompoundNBT()
        items.save(itemsTag)
        tag.put("Items", itemsTag)

        tag.putBoolean("IsGrinding", isGrinding)
        tag.putInt("Progress", progress)
        tag.putInt("TotalGrindCount", totalGrindCount)

        return tag
    }

    override fun tick() {
        if (isGrinding) {
            if (time < 30) {
                time++
            } else {
                time = 0
                isGrinding = false
                progress++
                updateInventory()
            }
        }
    }

    private fun updateInventory() {
        if (!level!!.isClientSide && progress >= totalGrindCount) {
            progress = 0
            items = RecipeHelper.getRecipeProductFor(RecipeRegistry.GRINDING, items, level!!)

            if (RecipeHelper.canCraftFromItem(RecipeRegistry.GRINDING, items, level!!)) {
                totalGrindCount = getTotalGrindingCycleCount()
            }
            setChanged()
        }
    }

    override fun getName(): ITextComponent {
        return (name ?: StringTextComponent(""))
    }

    fun setCustomName(text: ITextComponent) {
        name = text
    }

    override fun getCustomName(): ITextComponent? {
        return name
    }

    override fun setRecipeUsed(recipe: IRecipe<*>?) {
        if (recipe != null) {
            val location: ResourceLocation = recipe.id
            recipesUsed.addTo(location, 1)
        }
    }

    override fun getRecipeUsed(): IRecipe<*>? {
        return null
    }

    override fun fillStackedContents(helper: RecipeItemHelper) {
        helper.accountStack(items)
    }

    fun pushItems(inputStack: ItemStack) {
        if (!items.isEmpty) {
            WorldHelper.spawnItemStack(level!!, blockPos, items)
        }
        items = inputStack
        totalGrindCount = getTotalGrindingCycleCount()
        progress = 0
        isGrinding = false
        time = 0
        setChanged()
    }

    fun popItems(): ItemStack {
        val itemStack = items
        items = ItemStack.EMPTY
        totalGrindCount = 0
        progress = 0
        isGrinding = false
        time = 0
        setChanged()
        return itemStack
    }

    override fun onDataPacket(net: NetworkManager, pkt: SUpdateTileEntityPacket) {
        loadFromTag(pkt.tag)
    }

    override fun getUpdatePacket(): SUpdateTileEntityPacket {
        return SUpdateTileEntityPacket(blockPos, -1, saveToTag(CompoundNBT()))
    }

    override fun setChanged() {
        super.setChanged()
        if (!level!!.isClientSide)
            level?.sendBlockUpdated(blockPos, blockState, blockState, 2)
    }

    fun attemptGrinding() {
        if (!isGrinding and canGrind) {
            isGrinding = true
            setChanged()
        }
    }

    private fun getTotalGrindingCycleCount(): Int {
        return if (items.isEmpty) 0
        else
            RecipeHelper.getRecipeOptionalFor(RecipeRegistry.GRINDING, items, level!!)
                .map { obj: AbstractCookingRecipe -> obj.cookingTime }
                .orElse(3)
    }
}