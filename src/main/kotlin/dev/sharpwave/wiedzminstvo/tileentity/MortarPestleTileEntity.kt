package dev.sharpwave.wiedzminstvo.tileentity

import dev.sharpwave.wiedzminstvo.locale.AlchemyStrings
import dev.sharpwave.wiedzminstvo.registry.TileEntityRegistry
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.minecraft.block.BlockState
import net.minecraft.inventory.IRecipeHelperPopulator
import net.minecraft.inventory.IRecipeHolder
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.RecipeItemHelper
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.INameable
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent

class MortarPestleTileEntity : TileEntity(TileEntityRegistry.PESTLE), INameable, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {

    private var name: ITextComponent? = null
    var time = 0
        private set
    var isGrinding = false
    var items: ItemStack = ItemStack.EMPTY
        private set
    private val recipesUsed = Object2IntOpenHashMap<ResourceLocation>()

    override fun save(tag: CompoundNBT): CompoundNBT {
        super.save(tag)

        if (hasCustomName())
            tag.putString("CustomName", ITextComponent.Serializer.toJson(name!!))
        if (!items.isEmpty) {
            val itemsTag = CompoundNBT()
            items.save(itemsTag)
            tag.put("Items", itemsTag)
        }

        return tag
    }

    override fun load(state: BlockState, tag: CompoundNBT) {
        super.load(state, tag)

        if (tag.contains("CustomName", 8))
            name = ITextComponent.Serializer.fromJson(tag.getString("CustomName"))
        if (tag.contains("Items"))
            items = ItemStack.of(tag.getCompound("Items"))
    }

    override fun tick() {
       // if (isGrinding) {
            if (time < 20) {
                time++
            }
            else {
                time = 0
                isGrinding = false
            }
        //}
    }

    override fun getName(): ITextComponent {
        return (if (name != null) name else TranslationTextComponent(AlchemyStrings.ALCHEMY_TABLE_CONTAINER)) as ITextComponent
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
}