package dev.sharpwave.wiedzminstvo.items

import net.minecraft.block.Blocks
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

object ModItemGroup {
    val TAB_ALCHEMY = object : ItemGroup("wiedzminstvo_alchemy") {
        @OnlyIn(Dist.CLIENT)
        override fun makeIcon(): ItemStack {
            return ItemStack(Blocks.BREWING_STAND)
        }
    }.setRecipeFolderName("wiedzminstvo_alchemy")
    val TAB_EQUIPMENT = object : ItemGroup("wiedzminstvo_equipment") {
        @OnlyIn(Dist.CLIENT)
        override fun makeIcon(): ItemStack {
            return ItemStack(Items.ARMOR_STAND)
        }
    }.setRecipeFolderName("wiedzminstvo_equipment")
    val TAB_POTIONS = object : ItemGroup("wiedzminstvo_potions") {
        @OnlyIn(Dist.CLIENT)
        override fun makeIcon(): ItemStack {
            return ItemStack(Items.POTION)
        }
    }.setRecipeFolderName("wiedzminstvo_potions")
    val TAB_MISC = object : ItemGroup("wiedzminstvo_misc") {
        @OnlyIn(Dist.CLIENT)
        override fun makeIcon(): ItemStack {
            return ItemStack(Items.BOOKSHELF)
        }
    }.setRecipeFolderName("wiedzminstvo_misc")
}