package dev.sharpwave.wiedzminstvo.network.horse

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.sound.WhistleSounds
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
                val player: PlayerEntity? = WiedzminstvoMod.proxy.player
                if (player != null) {
                    val rand = Random()
                    player.level.playSound(
                        player,
                        player.x,
                        player.y,
                        player.z,
                        WhistleSounds.randomWhistle!!,
                        SoundCategory.PLAYERS,
                        1f,
                        (1.4 + rand.nextGaussian() / 3).toFloat()
                    )
                }
            }
        }
    }
}