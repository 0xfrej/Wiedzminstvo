package dev.sharpwave.wiedzminstvo.network.main

import dev.sharpwave.wiedzminstvo.network.AbstractNetwork
import dev.sharpwave.wiedzminstvo.network.main.horse.HorseSubNetwork

object MainNetwork : AbstractNetwork("main") {
    override fun init() {
        super.init()

        HorseSubNetwork.registerPackets(getBuilder()).registerNetworkChannel(channel)
    }
}