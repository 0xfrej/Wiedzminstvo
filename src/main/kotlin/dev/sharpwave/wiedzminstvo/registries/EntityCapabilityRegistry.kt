package dev.sharpwave.wiedzminstvo.registries

import dev.sharpwave.wiedzminstvo.entity.capabilities.horseowner.HorseOwner
import dev.sharpwave.wiedzminstvo.entity.capabilities.horseowner.HorseOwnerStorage
import dev.sharpwave.wiedzminstvo.entity.capabilities.horseowner.IHorseOwner
import dev.sharpwave.wiedzminstvo.entity.capabilities.storedhorse.HorseStorage
import dev.sharpwave.wiedzminstvo.entity.capabilities.storedhorse.IStoredHorse
import dev.sharpwave.wiedzminstvo.entity.capabilities.storedhorse.StoredHorse
import dev.sharpwave.wiedzminstvo.utils.CapabilityRegistrar

object EntityCapabilityRegistry {
    fun registerCapabilities() {
        CapabilityRegistrar
            .register(
                IHorseOwner::class.java,
                HorseOwner::class.java,
                HorseOwnerStorage()
            )
            .register(
                IStoredHorse::class.java,
                StoredHorse::class.java,
                HorseStorage()
            )
    }
}