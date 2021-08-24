package dev.sharpwave.wiedzminstvo.utils

import net.minecraft.world.server.ServerWorld
import net.minecraft.world.storage.DimensionSavedDataManager
import net.minecraft.world.storage.WorldSavedData

object Storage {
    fun <T : WorldSavedData> getStorageInstance(world: ServerWorld, resourceLocation: String, factory: () -> T): T {
        val storage: DimensionSavedDataManager = world.dataStorage
        var instance = storage.get(factory, resourceLocation)
        // if there is no cached or saved data, create plain instance
        if (instance == null) {
            instance = factory()
            storage.set(instance)
        }

        return instance
    }
}