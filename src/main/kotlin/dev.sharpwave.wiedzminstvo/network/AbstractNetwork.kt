package dev.sharpwave.wiedzminstvo.network

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.network.NetworkRegistry

abstract class AbstractNetwork(private val name: String) : AbstractBaseNetwork() {
    private var builder: NetworkBuilder? = null

    val channelName: String
        get() = "wiedzminstvo_${name}_channel"

    open fun init()
    {
        val version: String = WiedzminstvoMod.info.version.toString()

        registerNetworkChannel(
            NetworkRegistry.newSimpleChannel(
                ResourceLocation(WiedzminstvoMod.MODID, this.channelName),
                { version },
                { anObject: String? -> version == anObject }
            ) { anObject: String? -> version == anObject }
        )
    }

    protected fun getBuilder(): NetworkBuilder
    {
        if (builder == null) {
            builder = NetworkBuilder(channel)
        }
        return builder as NetworkBuilder
    }
}