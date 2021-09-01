package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.registry.BlockRegistry
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.blockLocation
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.getRegistryPath
import net.minecraft.block.BlockState
import net.minecraft.block.TallFlowerBlock
import net.minecraft.data.DataGenerator
import net.minecraft.state.properties.DoubleBlockHalf
import net.minecraftforge.client.model.generators.BlockStateProvider
import net.minecraftforge.client.model.generators.ConfiguredModel
import net.minecraftforge.client.model.generators.ModelFile
import net.minecraftforge.common.data.ExistingFileHelper

class BlockStates(generator: DataGenerator, existingFileHelper: ExistingFileHelper) :
    BlockStateProvider(generator, WiedzminstvoMod.MODID, existingFileHelper) {
    override fun registerStatesAndModels() {
        simpleBlock(BlockRegistry.ARENARIA)
        simpleBlock(BlockRegistry.BEGGARTICK)
        simpleBlock(BlockRegistry.BISON_GRASS)
        tallFlowerBlock(BlockRegistry.BLUE_LOTUS)
        tallFlowerBlock(BlockRegistry.WINTER_CHERRY)
        simpleBlock(BlockRegistry.FOOLS_PARSLEY)
        simpleBlock(BlockRegistry.BERBERCANE)
    }

    private fun tallFlowerBlock(block: TallFlowerBlock) {
        val top = ModelFile.UncheckedModelFile(blockLocation(getRegistryPath(block) + "_top"))
        val bottom = ModelFile.UncheckedModelFile(blockLocation(getRegistryPath(block) + "_bottom"))
        getVariantBuilder(block).forAllStates { state: BlockState ->
            ConfiguredModel.builder()
                .modelFile(if (state.getValue(TallFlowerBlock.HALF) == DoubleBlockHalf.UPPER) top else bottom)
                .build()
        }
    }
}