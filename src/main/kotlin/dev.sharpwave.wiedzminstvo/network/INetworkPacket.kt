package dev.sharpwave.wiedzminstvo.network

import net.minecraft.network.PacketBuffer

interface INetworkPacket {
    fun toBytes(buf: PacketBuffer)
}