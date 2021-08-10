package dev.sharpwave.wiedzminstvo.utils

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
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

    fun itemLocation(location: ResourceLocation): ResourceLocation {
        return location("item", location)
    }
    fun blockLocation(location: ResourceLocation): ResourceLocation {
        return location("block", location)
    }
    private fun location(location: String, resource: ResourceLocation): ResourceLocation {
        return ResourceLocation(WiedzminstvoMod.MODID, resource.path)
    }
}