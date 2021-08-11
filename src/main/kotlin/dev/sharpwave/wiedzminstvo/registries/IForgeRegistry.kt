package dev.sharpwave.wiedzminstvo.registries

import thedarkcolour.kotlinforforge.eventbus.KotlinEventBus

interface IForgeRegistry {
    fun register(bus: KotlinEventBus)
}