package dev.sharpwave.wiedzminstvo.network

import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

abstract class AbstractNetworkPacket {
    open fun toBytes(buf: PacketBuffer) {}
    abstract fun handle(ctx: Supplier<NetworkEvent.Context>)

    companion object : NetworkingUnit()
}