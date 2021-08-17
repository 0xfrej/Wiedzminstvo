package dev.sharpwave.wiedzminstvo.network.main.packets

import dev.sharpwave.wiedzminstvo.entity.managers.HorseManager.callHorse
import dev.sharpwave.wiedzminstvo.entity.managers.HorseManager.setHorse
import dev.sharpwave.wiedzminstvo.entity.managers.HorseManager.showHorseStats
import dev.sharpwave.wiedzminstvo.network.AbstractNetworkPacket
import dev.sharpwave.wiedzminstvo.network.NetworkingUnit
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

@Suppress("unused")
class PressKeyPacket() : AbstractNetworkPacket() {
    private var key = 0

    constructor(key: Int) : this() {
        this.key = key
    }

    constructor(buf: PacketBuffer) : this() {
        key = buf.readInt()
    }

    override fun toBytes(buf: PacketBuffer) {
        buf.writeInt(key)
    }

    override fun handle(ctx: Supplier<NetworkEvent.Context>) {
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
        ctx.get().packetHandled = true
    }

    companion object : NetworkingUnit()
}