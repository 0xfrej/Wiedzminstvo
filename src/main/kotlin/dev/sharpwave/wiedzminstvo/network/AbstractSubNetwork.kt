package dev.sharpwave.wiedzminstvo.network

abstract class AbstractSubNetwork : NetworkingUnit() {
    abstract fun registerPackets(builder: NetworkBuilder): AbstractSubNetwork
}