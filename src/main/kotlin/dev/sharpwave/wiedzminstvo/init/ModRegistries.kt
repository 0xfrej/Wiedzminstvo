package dev.sharpwave.wiedzminstvo.init

import dev.sharpwave.wiedzminstvo.registry.*
import thedarkcolour.kotlinforforge.eventbus.KotlinEventBus

object ModRegistries {
    fun register(bus: KotlinEventBus) {
        BlockRegistry.register(bus)
        ItemRegistry.register(bus)
        TileEntityRegistry.register(bus)
        GlobalLootModifiersRegistry.register(bus)
        ContainerTypeRegistry.register(bus)
    }
}