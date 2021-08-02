package dev.sharpwave.wiedzminstvo.network

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.network.NetworkRegistry
import net.minecraftforge.fml.network.simple.SimpleChannel

abstract class AbstractNetwork(channelName: String) {
    var instance: SimpleChannel? = null
        private set
    private val channelName: String = channelName

    val channel: String
        get() = "wiedzminstvo_${channelName}_channel"

    protected open fun init()
    {
        val version: String = WiedzminstvoMod.info.version.toString()

        instance =
            NetworkRegistry.newSimpleChannel(
                ResourceLocation(WiedzminstvoMod.MODID, this.channel),
                { version },
                { anObject: String? -> version == anObject }
            ) { anObject: String? -> version == anObject }
    }
}