package dev.sharpwave.wiedzminstvo.init

import dev.sharpwave.wiedzminstvo.registries.ItemRegistry
import thedarkcolour.kotlinforforge.eventbus.KotlinEventBus

object ModRegistries {
    fun register(bus: KotlinEventBus) {
        ItemRegistry.register(bus)
    }
}