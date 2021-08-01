package dev.sharpwave.wiedzminstvo

import dev.sharpwave.wiedzminstvo.client.gui.GuiStatViewer
import dev.sharpwave.wiedzminstvo.client.managers.KeybindManager
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent


@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
class ClientProxy : IProxy {
    override val world: World
        get() = Minecraft.getInstance().level as World
    override val player: PlayerEntity?
        get() = Minecraft.getInstance().player

    override fun displayStatViewer() {
        //Minecraft.getInstance().getC.displayGuiScreen(GuiStatViewer(player!!))
        Minecraft.getInstance().setScreen(GuiStatViewer(player!!))
    }

    companion object {
        @SubscribeEvent
        fun setup(event: FMLClientSetupEvent) {
            KeybindManager.init()
        }
    }
}