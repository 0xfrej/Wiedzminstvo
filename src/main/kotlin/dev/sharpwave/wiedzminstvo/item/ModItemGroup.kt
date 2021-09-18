package dev.sharpwave.wiedzminstvo.item

import net.minecraft.block.Blocks
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

object ModItemGroup {
    val TAB_ALCHEMY: ItemGroup = object : ItemGroup("wiedzminstvo_alchemy") {
        @OnlyIn(Dist.CLIENT)
        override fun makeIcon(): ItemStack {
            return ItemStack(Blocks.BREWING_STAND)
        }
    }.setRecipeFolderName("alchemy")
    val TAB_POTIONS: ItemGroup = object : ItemGroup("wiedzminstvo_potions") {
        @OnlyIn(Dist.CLIENT)
        override fun makeIcon(): ItemStack {
            return ItemStack(Items.POTION)
        }
    }.setRecipeFolderName("potions")
    val TAB_MISC: ItemGroup = object : ItemGroup("wiedzminstvo_misc") {
        @OnlyIn(Dist.CLIENT)
        override fun makeIcon(): ItemStack {
            return ItemStack(Items.BOOKSHELF)
        }
    }.setRecipeFolderName("misc")
}