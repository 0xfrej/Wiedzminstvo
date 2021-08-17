package dev.sharpwave.wiedzminstvo.client.event

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.client.managers.KeybindManager
import dev.sharpwave.wiedzminstvo.config.HorseConfig
import dev.sharpwave.wiedzminstvo.network.main.packets.PressKeyPacket
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.event.TickEvent.PlayerTickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.LogicalSide
import net.minecraftforge.fml.common.Mod.EventBusSubscriber


@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = [Dist.CLIENT], modid = WiedzminstvoMod.MODID)
object KeybindEvents {
    private var lastPressTime: Long = 0
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    fun onPlayerTick(event: PlayerTickEvent) {
        val player = event.player
        if (player != null && event.side == LogicalSide.CLIENT) {
            handleKey(KeybindManager.callHorse!!.isDown, { PressKeyPacket.sendToServer(PressKeyPacket(0)) })
            handleKey(KeybindManager.setHorse!!.isDown, { PressKeyPacket.sendToServer(PressKeyPacket(1)) })
            handleKey(if (HorseConfig.enableStatsViewer?.get() != null) KeybindManager.showStats!!.isDown else false, { PressKeyPacket.sendToServer(
                PressKeyPacket(2)
            ) })
        }
    }

    private fun handleKey(condition: Boolean, callback: () -> Unit, debounceTime: Int = 500) {
        if (condition)
            debounce(callback, debounceTime)
    }

    private fun debounce(callback: () -> Unit, debounceTime: Int = 500) {
        if (System.currentTimeMillis() - lastPressTime > debounceTime) {
            lastPressTime = System.currentTimeMillis()
            callback()
        }
    }
}