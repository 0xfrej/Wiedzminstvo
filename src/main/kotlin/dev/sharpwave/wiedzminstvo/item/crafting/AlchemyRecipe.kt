package dev.sharpwave.wiedzminstvo.item.crafting

import dev.sharpwave.wiedzminstvo.inventory.AlchemyInventory
import net.minecraft.block.FlowerBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.item.SuspiciousStewItem
import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.item.crafting.IRecipeType
import net.minecraft.tags.ItemTags
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

class AlchemyRecipe(location: ResourceLocation) : IRecipe<AlchemyInventory> {
    override fun matches(inventory: AlchemyInventory, level: World): Boolean {
        /*var flag = false
        var flag1 = false
        var flag2 = false
        var flag3 = false
        for (i in 0 until inventory.containerSize) {
            val itemstack = inventory.getItem(i)
            if (!itemstack.isEmpty) {
                if (itemstack.item === Blocks.BROWN_MUSHROOM.asItem() && !flag2) {
                    flag2 = true
                } else if (itemstack.item === Blocks.RED_MUSHROOM.asItem() && !flag1) {
                    flag1 = true
                } else if (itemstack.item.`is`(ItemTags.SMALL_FLOWERS) && !flag) {
                    flag = true
                } else {
                    if (itemstack.item !== Items.BOWL || flag3) {
                        return false
                    }
                    flag3 = true
                }
            }
        }*/
        return false //flag && flag2 && flag1 && flag3
    }

    override fun assemble(inventory: AlchemyInventory): ItemStack {
        var items = ItemStack.EMPTY
        for (i in 0 until inventory.containerSize) {
            val inventoryItem = inventory.getItem(i)
            if (!inventoryItem.isEmpty && inventoryItem.item.`is`(ItemTags.SMALL_FLOWERS)) {
                items = inventoryItem
                break
            }
        }
        val outputItem = ItemStack(Items.SUSPICIOUS_STEW, 1)
        if (items.item is BlockItem && (items.item as BlockItem).block is FlowerBlock) {
            val flowerBlock = (items.item as BlockItem).block as FlowerBlock
            val effect = flowerBlock.suspiciousStewEffect
            SuspiciousStewItem.saveMobEffect(outputItem, effect, flowerBlock.effectDuration)
        }
        return outputItem
    }

    override fun canCraftInDimensions(p_194133_1_: Int, p_194133_2_: Int): Boolean {
        return p_194133_1_ >= 2 && p_194133_2_ >= 2
    }

    override fun getSerializer(): IRecipeSerializer<*> {
        return IRecipeSerializer.SUSPICIOUS_STEW
    }

    override fun getResultItem(): ItemStack {
        TODO("Not yet implemented")
    }

    override fun getId(): ResourceLocation {
        TODO("Not yet implemented")
    }

    override fun getType(): IRecipeType<*> {
        TODO("Not yet implemented")
    }
}