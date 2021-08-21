package dev.sharpwave.wiedzminstvo.init

import dev.sharpwave.wiedzminstvo.client.gui.screen.AlchemyScreen
import dev.sharpwave.wiedzminstvo.registry.ContainerTypeRegistry
import net.minecraft.client.gui.ScreenManager
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
object ModScreens {
    @SubscribeEvent
    fun onClientSetup(@Suppress("UNUSED_PARAMETER") event: FMLCommonSetupEvent) {
        ScreenManager.register(ContainerTypeRegistry.ALCHEMY) { container, inv, title ->
            AlchemyScreen(
                container,
                inv,
                title
            )
        }
    }
}