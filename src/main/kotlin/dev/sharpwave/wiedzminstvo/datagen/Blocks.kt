package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.registry.BlockRegistry
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.blockLocation
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.getRegistryPath
import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.BlockModelProvider
import net.minecraftforge.common.data.ExistingFileHelper

class Blocks(generator: DataGenerator, existingFileHelper: ExistingFileHelper) : BlockModelProvider(generator, WiedzminstvoMod.MODID, existingFileHelper) {

    override fun registerModels() {
        cross(getRegistryPath(BlockRegistry.ARENARIA), blockLocation(getRegistryPath(BlockRegistry.ARENARIA)))
        cross(getRegistryPath(BlockRegistry.BEGGARTICK), blockLocation(getRegistryPath(BlockRegistry.BEGGARTICK)))
        cross(getRegistryPath(BlockRegistry.BISON_GRASS), blockLocation(getRegistryPath(BlockRegistry.BISON_GRASS)))
        tallCross(getRegistryPath(BlockRegistry.BLUE_LOTUS), getRegistryPath(BlockRegistry.BLUE_LOTUS))
        tallCross(getRegistryPath(BlockRegistry.WINTER_CHERRY), getRegistryPath(BlockRegistry.WINTER_CHERRY))
    }

    private fun tallCross(name: String, registryPath: String) {
        cross(name+"_top", blockLocation(registryPath+"_top"))
        cross(name+"_bottom", blockLocation(registryPath+"_bottom"))
    }
}