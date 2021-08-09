package dev.sharpwave.wiedzminstvo.network

import net.minecraft.network.IPacket
import net.minecraft.network.NetworkManager
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkEvent
import net.minecraftforge.fml.network.PacketDistributor.PacketTarget
import net.minecraftforge.fml.network.simple.SimpleChannel
import net.minecraftforge.fml.network.simple.SimpleChannel.MessageBuilder

open class NetworkingUnit {
    lateinit var channel: SimpleChannel
        private set

    fun registerNetworkChannel(channel: SimpleChannel): NetworkingUnit {
        this.channel = channel
        return this
    }

    open fun <MSG> encodeMessage(message: MSG, target: PacketBuffer): Int {
        return channel.encodeMessage(message, target)
    }

    open fun <MSG> sendToServer(message: MSG) {
        channel.sendToServer(message)
    }

    open fun <MSG> sendTo(message: MSG, manager: NetworkManager, direction: NetworkDirection) {
        channel.sendTo(message, manager, direction)
    }

    open fun <MSG> send(target: PacketTarget, message: MSG) {
        channel.send(target, message)
    }

    open fun <MSG> toVanillaPacket(message: MSG, direction: NetworkDirection): IPacket<*> {
        return channel.toVanillaPacket(message, direction)
    }

    open fun <MSG> reply(msgToReply: MSG, context: NetworkEvent.Context) {
        channel.reply(msgToReply, context)
    }

    open fun isRemotePresent(manager: NetworkManager): Boolean {
        return channel.isRemotePresent(manager)
    }

    open fun <M> messageBuilder(type: Class<M>, id: Int): MessageBuilder<M> {
        return channel.messageBuilder(type, id)
    }

    open fun <M> messageBuilder(type: Class<M>, id: Int, direction: NetworkDirection): MessageBuilder<M> {
        return channel.messageBuilder(type, id, direction)
    }
}