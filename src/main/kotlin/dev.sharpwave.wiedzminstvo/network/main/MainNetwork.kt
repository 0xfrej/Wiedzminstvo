package dev.sharpwave.wiedzminstvo.network.main

import dev.sharpwave.wiedzminstvo.network.AbstractNetwork
import dev.sharpwave.wiedzminstvo.network.main.horse.HorseSubNetwork
import dev.sharpwave.wiedzminstvo.network.main.horse.packets.PlayWhistlePacket
import net.minecraftforge.fml.network.NetworkDirection

object MainNetwork : AbstractNetwork("main") {
    override fun init() {
        super.init()
        registerPackets()

        HorseSubNetwork.registerPackets(getBuilder()).registerNetworkChannel(channel)
    }

    private fun registerPackets() {
        getBuilder()
            .registerMessage(
                PlayWhistlePacket::class.java,
                NetworkDirection.PLAY_TO_CLIENT
            )
    }
}