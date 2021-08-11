package dev.sharpwave.wiedzminstvo.init

import dev.sharpwave.wiedzminstvo.registries.EntityCapabilityRegistry
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
object ModCapabilities {
    @SubscribeEvent
    fun onCommonSetup(@Suppress("UNUSED_PARAMETER") event: FMLCommonSetupEvent) {
        EntityCapabilityRegistry.registerCapabilities()
    }
}