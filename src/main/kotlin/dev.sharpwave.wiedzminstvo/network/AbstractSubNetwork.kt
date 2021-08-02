package dev.sharpwave.wiedzminstvo.network

import net.minecraftforge.fml.network.simple.SimpleChannel

abstract class AbstractSubNetwork : AbstractBaseNetwork() {
    abstract fun registerPackets(builder: NetworkBuilder): AbstractSubNetwork
}