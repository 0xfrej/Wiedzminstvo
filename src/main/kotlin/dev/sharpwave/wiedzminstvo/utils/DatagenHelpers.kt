package dev.sharpwave.wiedzminstvo.utils

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation

object DatagenHelpers {
    fun handheldItem(): ResourceLocation {
        return ResourceLocation("item/handheld")
    }

    fun itemLocation(itemName: String): ResourceLocation {
        return location("item", itemName)
    }
    fun blockLocation(itemName: String): ResourceLocation {
        return location("block", itemName)
    }
    private fun location(location: String, itemName: String): ResourceLocation {
        return ResourceLocation(WiedzminstvoMod.MODID, "$location/$itemName")
    }

    fun getRegistryPath(item: Item): String {
        return item.registryName!!.path;
    }

    fun getRegistryPath(block: Block): String {
        return block.registryName!!.path;
    }
}