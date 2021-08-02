package dev.sharpwave.wiedzminstvo.network

import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

interface INetworkPacket {
    fun toBytes(buf: PacketBuffer) {}
    fun handle(ctx: Supplier<NetworkEvent.Context>)
}