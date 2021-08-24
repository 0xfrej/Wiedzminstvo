package dev.sharpwave.wiedzminstvo.network

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.network.NetworkRegistry

abstract class AbstractNetwork(private val name: String) : NetworkingUnit() {
    private var builder: NetworkBuilder? = null

    open fun init() {
        val version: String = WiedzminstvoMod.info.version.toString()

        registerNetworkChannel(
            NetworkRegistry.newSimpleChannel(
                ResourceLocation(WiedzminstvoMod.MODID, this.name),
                { version },
                { anObject: String? -> version == anObject }
            ) { anObject: String? -> version == anObject }
        )
    }

    protected fun getBuilder(): NetworkBuilder {
        if (builder == null) {
            builder = NetworkBuilder(channel)
        }
        return builder as NetworkBuilder
    }
}