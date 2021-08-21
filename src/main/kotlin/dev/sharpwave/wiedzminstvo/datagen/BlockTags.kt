package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.tag.EntityTypeTags
import net.minecraft.data.BlockTagsProvider
import net.minecraft.data.DataGenerator
import net.minecraft.data.EntityTypeTagsProvider
import net.minecraft.data.ItemTagsProvider
import net.minecraft.entity.EntityType
import net.minecraftforge.common.data.ExistingFileHelper

class BlockTags(generator: DataGenerator, existingFileHelper: ExistingFileHelper) : BlockTagsProvider(generator, WiedzminstvoMod.MODID, existingFileHelper) {
    override fun addTags() {
    }
}