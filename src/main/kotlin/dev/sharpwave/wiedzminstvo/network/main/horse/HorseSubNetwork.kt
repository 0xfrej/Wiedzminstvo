package dev.sharpwave.wiedzminstvo.network.main.horse

import dev.sharpwave.wiedzminstvo.network.AbstractSubNetwork
import dev.sharpwave.wiedzminstvo.network.NetworkBuilder
import dev.sharpwave.wiedzminstvo.network.main.horse.packets.HorseCapSyncPacket
import dev.sharpwave.wiedzminstvo.network.main.horse.packets.OwnerSyncShowStatsPacket
import dev.sharpwave.wiedzminstvo.network.main.horse.packets.PressKeyPacket
import net.minecraftforge.fml.network.NetworkDirection

@Suppress("INACCESSIBLE_TYPE")
object HorseSubNetwork : AbstractSubNetwork() {

    override fun registerPackets(builder: NetworkBuilder): AbstractSubNetwork {
        builder
            .registerMessage(
                HorseCapSyncPacket::class,
                NetworkDirection.PLAY_TO_CLIENT
            )
            .registerMessage(
                OwnerSyncShowStatsPacket::class,
                NetworkDirection.PLAY_TO_CLIENT
            )
            .registerMessage(
                PressKeyPacket::class,
                NetworkDirection.PLAY_TO_SERVER
            )

        return this
    }
}