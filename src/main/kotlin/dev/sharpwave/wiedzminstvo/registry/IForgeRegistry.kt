package dev.sharpwave.wiedzminstvo.registry

import thedarkcolour.kotlinforforge.eventbus.KotlinEventBus

interface IForgeRegistry {
    fun register(bus: KotlinEventBus)
}