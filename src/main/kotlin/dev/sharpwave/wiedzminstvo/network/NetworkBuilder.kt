package dev.sharpwave.wiedzminstvo.network

import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkEvent
import net.minecraftforge.fml.network.simple.SimpleChannel
import java.util.*
import java.util.function.Supplier
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.functions


class NetworkBuilder(private val network: SimpleChannel){
    private var messageIndexCount = 0

    fun <MSG : AbstractNetworkPacket> registerMessage(
        messageType: KClass<MSG>,
        networkDirection: NetworkDirection,
        encoder: (MSG, PacketBuffer) -> Unit =
            { obj: MSG, buf: PacketBuffer -> obj.toBytes(buf) },
        decoder: ((PacketBuffer) -> MSG) =
            { buf: PacketBuffer -> messageType.java.getConstructor(PacketBuffer::class.java).newInstance(buf) },
        messageConsumer: ((MSG, Supplier<NetworkEvent.Context>) -> Unit) =
            { obj: MSG, ctx: Supplier<NetworkEvent.Context> -> obj.handle(ctx) },
    ) : NetworkBuilder {
        @Suppress("INACCESSIBLE_TYPE")
        network.registerMessage(
            messageIndexCount++,
            messageType.java,
            encoder,
            decoder,
            messageConsumer,
            Optional.of(networkDirection)
        )

        val functionEx = messageType.companionObject!!.functions.first { it.name == "registerNetworkChannel" }
        functionEx.call(messageType.companionObjectInstance!!, network)

        return this
    }
}