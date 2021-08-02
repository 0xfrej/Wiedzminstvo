package dev.sharpwave.wiedzminstvo.init

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import net.minecraft.util.ResourceLocation
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.network.NetworkRegistry

@Mod.EventBusSubscriber
object ModNetwork {
    @SubscribeEvent
    fun onCommonSetup(event: FMLCommonSetupEvent) {

    }
}