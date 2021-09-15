package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.block.GrowableFlowerBlock
import dev.sharpwave.wiedzminstvo.block.GrowableTallFlowerBlock
import dev.sharpwave.wiedzminstvo.registry.BlockRegistry
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.blockLocation
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.getRegistryPath
import net.minecraft.block.BlockState
import net.minecraft.block.DoublePlantBlock
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
        simpleBlock(BlockRegistry.CORTINARIUS)
        simpleBlock(BlockRegistry.PUFFBALL)
        growableTallFlower(BlockRegistry.BLUE_LOTUS)
        growableTallFlower(BlockRegistry.WINTER_CHERRY)
        growableFlower(BlockRegistry.FOOLS_PARSLEY)
        growableFlower(BlockRegistry.BERBERCANE)
        growableFlower(BlockRegistry.BALLISEFRUIT)
        growableFlower(BlockRegistry.CELANDINE)
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
    private fun growableTallFlower(block: GrowableTallFlowerBlock) {
        val modelBaseName = getRegistryPath(block)
        val bottom = models().cross(modelBaseName + "_bottom", blockLocation(modelBaseName + "_bottom"))

        // TODO: If possible try cut age states for bottom part
        getVariantBuilder(block).forAllStates { state: BlockState ->
            var variantModel = bottom
            val half = state.getValue(DoublePlantBlock.HALF)
            if (half == DoubleBlockHalf.UPPER) {
                val age = state.getValue(GrowableTallFlowerBlock.AGE)
                val top = models().cross(modelBaseName + "_top_stage${age}", blockLocation(modelBaseName + "_top_stage${age}"))
                variantModel = top
            }
            ConfiguredModel.builder()
                .modelFile(variantModel)
                .build()
        }
    }
    private fun growableFlower(block: GrowableFlowerBlock) {
        val modelBaseName = getRegistryPath(block)

        getVariantBuilder(block).forAllStates { state: BlockState ->
            val age = state.getValue(GrowableTallFlowerBlock.AGE)
            val model = models().cross(modelBaseName + "_stage${age}", blockLocation(modelBaseName + "_stage${age}"))

            ConfiguredModel.builder()
                .modelFile(model)
                .build()
        }
    }
}