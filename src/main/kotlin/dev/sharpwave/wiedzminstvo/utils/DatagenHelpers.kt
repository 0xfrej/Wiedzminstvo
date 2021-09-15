package dev.sharpwave.wiedzminstvo.utils

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.loot.GlobalLootModifierSerializer

object DatagenHelpers {
    fun handheldItem(): ResourceLocation {
        return ResourceLocation("item/handheld")
    }

    fun generatedItem(): ResourceLocation {
        return ResourceLocation("item/generated")
    }

    fun itemLocation(itemName: String): ResourceLocation {
        return location("item", itemName)
    }

    fun blockLocation(itemName: String): ResourceLocation {
        return location("block", itemName)
    }

    fun tallBlockLocation(itemName: String): ResourceLocation {
        return blockLocation(itemName + "_top")
    }

    fun growableTallFlowerBlockLocation(itemName: String): ResourceLocation {
        return blockLocation(itemName + "_top_stage3")
    }

    fun growableFlowerBlockLocation(itemName: String): ResourceLocation {
        return blockLocation(itemName + "_stage3")
    }

    private fun location(location: String, itemName: String): ResourceLocation {
        return ResourceLocation(WiedzminstvoMod.MODID, "$location/$itemName")
    }

    fun getRegistryPath(item: Item): String {
        return item.registryName!!.path
    }

    fun getRegistryPath(block: Block): String {
        return block.registryName!!.path
    }

    fun getRegistryPath(serializer: GlobalLootModifierSerializer<*>): String {
        return serializer.registryName!!.path
    }
}