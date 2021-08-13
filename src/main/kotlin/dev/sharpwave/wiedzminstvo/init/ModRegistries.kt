package dev.sharpwave.wiedzminstvo.init

import dev.sharpwave.wiedzminstvo.registries.BlockRegistry
import dev.sharpwave.wiedzminstvo.registries.ItemRegistry
import thedarkcolour.kotlinforforge.eventbus.KotlinEventBus

object ModRegistries {
    fun register(bus: KotlinEventBus) {
        BlockRegistry.register(bus)
        ItemRegistry.register(bus)
    }
}