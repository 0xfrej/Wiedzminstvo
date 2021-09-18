package dev.sharpwave.wiedzminstvo.block.mixins

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import net.minecraft.block.Block
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.ForgeRegistries
import java.util.*

open class Shearable: IShearable{
    private lateinit var parent: Block
    protected var harvestItem: Item? = null

    override fun withShearable(parent: Block) {
        this.parent = parent
    }

    override fun getShearable(): IShearable {
        return this
    }

    override fun asHarvestedItem(): Item {
        if (this.harvestItem == null) {
            this.harvestItem = ForgeRegistries.ITEMS.getValue(
                ResourceLocation(
                    WiedzminstvoMod.MODID,
                    "harvested_" + parent.asItem().toString()
                )
            )
        }
        return this.harvestItem!!.delegate.get()
    }

    override fun harvest(tool: ItemStack): ItemStack {
        return if (tool.isEnchanted) {
            val enchantments = EnchantmentHelper.getEnchantments(tool)
            val fortuneLevel = enchantments[Enchantments.BLOCK_FORTUNE]!!
            ItemStack(asHarvestedItem(), 1 + additionalHarvestedItems(fortuneLevel))
        }
        else ItemStack(asHarvestedItem(), 1)
    }

    protected fun additionalHarvestedItems(fortuneLevel: Int): Int {
        val shouldDrop = RANDOM.nextInt(4 - fortuneLevel)
        return if (shouldDrop > 0) {
            fortuneLevel
        }
        else 0
    }

    companion object {
        private val RANDOM = Random()
    }
}