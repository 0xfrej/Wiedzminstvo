package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.registries.BlockRegistry
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.blockLocation
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.getRegistryPath
import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.BlockModelProvider
import net.minecraftforge.common.data.ExistingFileHelper

class Blocks(generator: DataGenerator, existingFileHelper: ExistingFileHelper) : BlockModelProvider(generator, WiedzminstvoMod.MODID, existingFileHelper) {

    override fun registerModels() {
        cross(getRegistryPath(BlockRegistry.ARENARIA), blockLocation(getRegistryPath(BlockRegistry.ARENARIA)))
        cross(getRegistryPath(BlockRegistry.BEGGARTICK), blockLocation(getRegistryPath(BlockRegistry.BEGGARTICK)))
    }
}