package dev.sharpwave.wiedzminstvo.network.main.horse.packets

import dev.sharpwave.wiedzminstvo.managers.HorseManager.callHorse
import dev.sharpwave.wiedzminstvo.managers.HorseManager.setHorse
import dev.sharpwave.wiedzminstvo.managers.HorseManager.showHorseStats
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier


class PressKeyPacket {
    private var key = 0

    constructor() {}
    constructor(key: Int) {
        this.key = key
    }

    constructor(buf: PacketBuffer) {
        key = buf.readInt()
    }

    fun toBytes(buf: PacketBuffer) {
        buf.writeInt(key)
    }

    fun handle(ctx: Supplier<NetworkEvent.Context>) {
        if (ctx.get().direction.receptionSide.isServer) {
            ctx.get().enqueueWork {
                val player = ctx.get().sender
                if (player != null) {
                    when (key) {
                        0 -> callHorse(player)
                        1 -> setHorse(player)
                        2 -> showHorseStats(player)
                    }
                }
            }
        }
    }
}