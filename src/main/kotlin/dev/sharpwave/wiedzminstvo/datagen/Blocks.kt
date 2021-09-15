package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.registry.BlockRegistry
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.blockLocation
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.getRegistryPath
import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.BlockModelProvider
import net.minecraftforge.common.data.ExistingFileHelper

class Blocks(generator: DataGenerator, existingFileHelper: ExistingFileHelper) :
    BlockModelProvider(generator, WiedzminstvoMod.MODID, existingFileHelper) {

    override fun registerModels() {
        cross(getRegistryPath(BlockRegistry.ARENARIA))
        cross(getRegistryPath(BlockRegistry.BEGGARTICK))
        cross(getRegistryPath(BlockRegistry.BISON_GRASS))
        cross(getRegistryPath(BlockRegistry.CORTINARIUS))
        cross(getRegistryPath(BlockRegistry.PUFFBALL))
    }

    private fun cross(name: String) {
        cross(name, blockLocation(name))
    }

    private fun tallCross(registryPath: String) {
        cross(registryPath + "_top", blockLocation(registryPath + "_top"))
        cross(registryPath + "_bottom", blockLocation(registryPath + "_bottom"))
    }
}