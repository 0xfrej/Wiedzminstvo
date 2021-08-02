package dev.sharpwave.wiedzminstvo.network

import dev.sharpwave.wiedzminstvo.network.main.horse.packets.HorseCapSyncPacket
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkEvent
import net.minecraftforge.fml.network.simple.SimpleChannel
import java.util.*
import java.util.function.Supplier


class NetworkBuilder(private val network: SimpleChannel){
    private var messageIndexCount = 0

    fun <MSG : INetworkPacket> registerMessage(
        messageType: Class<MSG>,
        networkDirection: NetworkDirection,
        encoder: (MSG, PacketBuffer) -> Unit =
            { obj: MSG, buf: PacketBuffer -> obj.toBytes(buf) },
        decoder: ((PacketBuffer) -> MSG) =
            { buf: PacketBuffer -> messageType.getConstructor(PacketBuffer::class.java).newInstance(buf) },
        messageConsumer: ((MSG, Supplier<NetworkEvent.Context>) -> Unit) =
            { obj: MSG, ctx: Supplier<NetworkEvent.Context> -> obj.handle(ctx) },
    ) : NetworkBuilder {
        @Suppress("INACCESSIBLE_TYPE")
        network.registerMessage(
            messageIndexCount++,
            messageType,
            encoder,
            decoder,
            messageConsumer,
            Optional.of(networkDirection)
        )

        return this
    }
}