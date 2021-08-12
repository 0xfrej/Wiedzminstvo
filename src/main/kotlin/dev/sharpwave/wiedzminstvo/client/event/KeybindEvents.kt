package dev.sharpwave.wiedzminstvo.client.event

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.client.managers.KeybindManager
import dev.sharpwave.wiedzminstvo.config.HorseConfig
import dev.sharpwave.wiedzminstvo.network.main.horse.packets.PressKeyPacket
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
            val callHorse: Boolean = KeybindManager.callHorse!!.isDown
            val setHorse: Boolean = KeybindManager.setHorse!!.isDown
            val showStats = if (HorseConfig.enableStatsViewer?.get() != null) KeybindManager.showStats!!.isDown else false
            if (callHorse) {
                if (System.currentTimeMillis() - lastPressTime > 500) {
                    lastPressTime = System.currentTimeMillis()
                    PressKeyPacket.sendToServer(PressKeyPacket(0))
                }
            }
            if (setHorse) {
                if (System.currentTimeMillis() - lastPressTime > 500) {
                    lastPressTime = System.currentTimeMillis()
                    PressKeyPacket.sendToServer(PressKeyPacket(1))
                }
            }
            if (showStats) {
                if (System.currentTimeMillis() - lastPressTime > 500) {
                    lastPressTime = System.currentTimeMillis()
                    PressKeyPacket.sendToServer(PressKeyPacket(2))
                }
            }
        }
    }
}