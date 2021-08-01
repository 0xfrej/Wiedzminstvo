package dev.sharpwave.wiedzminstvo.init

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent

interface ISidedInitialization {
    fun onCommonSetup(event: FMLCommonSetupEvent) {}
    fun onClientSetup(event: FMLClientSetupEvent) {}
    fun onServerAboutToStart(event: FMLServerAboutToStartEvent) {}
}