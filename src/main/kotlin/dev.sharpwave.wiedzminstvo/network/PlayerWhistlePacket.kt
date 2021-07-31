package dev.sharpwave.wiedzminstvo.network

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.sound.WhistleSounds.randomWhistle
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.network.PacketBuffer
import net.minecraft.util.SoundCategory
import net.minecraftforge.fml.network.NetworkEvent
import java.util.*
import java.util.function.Supplier


class PlayWhistlePacket {
    constructor() {}
    constructor(buf: PacketBuffer?) {}

    fun toBytes(buf: PacketBuffer?) {}
    fun handle(ctx: Supplier<NetworkEvent.Context>) {
        if (ctx.get().direction.receptionSide.isClient) {
            ctx.get().enqueueWork {
                val player: PlayerEntity = WiedzminstvoMod.proxy.getPlayer()
                if (player != null) {
                    val rand = Random()
                    player.world.playSound(
                        player,
                        player.getPosition(),
                        randomWhistle,
                        SoundCategory.PLAYERS,
                        1f,
                        (1.4 + rand.nextGaussian() / 3).toFloat()
                    )
                }
            }
        }
    }
}