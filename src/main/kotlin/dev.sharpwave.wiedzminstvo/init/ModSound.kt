package dev.sharpwave.wiedzminstvo.init

import dev.sharpwave.wiedzminstvo.sound.WhistleSounds
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
object ModSound {

    @SubscribeEvent
    fun init(event: FMLCommonSetupEvent) {
        WhistleSounds.registerSounds()
    }
}