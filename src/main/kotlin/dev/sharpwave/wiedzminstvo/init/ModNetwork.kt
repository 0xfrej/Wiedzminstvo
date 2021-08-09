package dev.sharpwave.wiedzminstvo.init

import dev.sharpwave.wiedzminstvo.network.main.MainNetwork
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
object ModNetwork {
    @SubscribeEvent
    fun onCommonSetup(event: FMLCommonSetupEvent) {
        MainNetwork.init()
    }
}