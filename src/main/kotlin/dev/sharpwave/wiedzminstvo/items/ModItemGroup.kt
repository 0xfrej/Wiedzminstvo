package dev.sharpwave.wiedzminstvo.items

import net.minecraft.block.Blocks
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

object ModItemGroup {
    val TAB_ALCHEMY = object : ItemGroup("wiedzminstvo_alchemy") {
        @OnlyIn(Dist.CLIENT)
        override fun makeIcon(): ItemStack {
            return ItemStack(Blocks.BREWING_STAND)
        }
    }.setRecipeFolderName("wiedzminstvo_alchemy")
}