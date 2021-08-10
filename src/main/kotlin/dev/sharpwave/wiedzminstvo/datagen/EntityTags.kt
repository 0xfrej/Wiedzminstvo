package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.entity.EntityTypeTags
import net.minecraft.data.DataGenerator
import net.minecraft.data.EntityTypeTagsProvider
import net.minecraft.entity.EntityType
import net.minecraftforge.common.data.ExistingFileHelper

class EntityTags(generator: DataGenerator, existingFileHelper: ExistingFileHelper) : EntityTypeTagsProvider(generator, WiedzminstvoMod.MODID, existingFileHelper) {
    override fun addTags() {
        tag(EntityTypeTags.HORSES).add(EntityType.HORSE, EntityType.SKELETON_HORSE, EntityType.ZOMBIE_HORSE, EntityType.DONKEY, EntityType.MULE, EntityType.LLAMA)
    }
}