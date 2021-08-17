package dev.sharpwave.wiedzminstvo.init

import dev.sharpwave.wiedzminstvo.registry.BlockRegistry
import dev.sharpwave.wiedzminstvo.registry.GlobalLootModifiersRegistry
import dev.sharpwave.wiedzminstvo.registry.ItemRegistry
import thedarkcolour.kotlinforforge.eventbus.KotlinEventBus

object ModRegistries {
    fun register(bus: KotlinEventBus) {
        BlockRegistry.register(bus)
        ItemRegistry.register(bus)
        GlobalLootModifiersRegistry.register(bus)
    }
}