package dev.sharpwave.wiedzminstvo.utils

import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityManager
import java.util.concurrent.Callable

object CapabilityRegistrar {
    fun <IT, T:IT> register(
        interfaceType: Class<IT>,
        type: Class<T>,
        storage: Capability.IStorage<IT>,
        factory: () -> IT = { type.getConstructor().newInstance() as IT }
    ) : CapabilityRegistrar {
        CapabilityManager.INSTANCE.register(
            interfaceType,
            storage,
            factory
        )

        return this
    }
}