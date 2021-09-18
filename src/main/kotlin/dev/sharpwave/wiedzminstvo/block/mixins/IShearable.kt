package dev.sharpwave.wiedzminstvo.block.mixins

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

interface IShearable {
    fun withShearable(parent: Block)
    fun getShearable(): IShearable
    fun asHarvestedItem(): Item
    fun harvest(tool: ItemStack): ItemStack
}