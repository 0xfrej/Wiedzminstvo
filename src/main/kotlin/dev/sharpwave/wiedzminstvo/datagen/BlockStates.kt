package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.registries.BlockRegistry
import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.BlockStateProvider
import net.minecraftforge.common.data.ExistingFileHelper

class BlockStates(generator: DataGenerator, existingFileHelper: ExistingFileHelper) : BlockStateProvider(generator, WiedzminstvoMod.MODID, existingFileHelper) {
    override fun registerStatesAndModels() {
        simpleBlock(BlockRegistry.ARENARIA)
        simpleBlock(BlockRegistry.BEGGARTICK)
    }
}