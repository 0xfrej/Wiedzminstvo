package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import net.minecraft.data.BlockTagsProvider
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.ExistingFileHelper

class BlockTags(generator: DataGenerator, existingFileHelper: ExistingFileHelper) :
    BlockTagsProvider(generator, WiedzminstvoMod.MODID, existingFileHelper) {
    override fun addTags() {
    }
}