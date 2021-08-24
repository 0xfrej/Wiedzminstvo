package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.registry.ItemRegistry
import net.minecraft.data.BlockTagsProvider
import net.minecraft.data.DataGenerator
import net.minecraft.data.ItemTagsProvider
import net.minecraftforge.common.data.ExistingFileHelper
import dev.sharpwave.wiedzminstvo.tag.ItemTags as ItemTypeTags

class ItemTags(generator: DataGenerator, blockTags: BlockTagsProvider, existingFileHelper: ExistingFileHelper) :
    ItemTagsProvider(generator, blockTags, WiedzminstvoMod.MODID, existingFileHelper) {
    override fun addTags() {
        tag(ItemTypeTags.ALCHEMY_INGREDIENTS).add(ItemRegistry.BLUE_LOTUS, ItemRegistry.WINTER_CHERRY, ItemRegistry.ARENARIA, ItemRegistry.BEGGARTICK, ItemRegistry.BISON_GRASS)
        tag(ItemTypeTags.ALCHEMY_FUSION_INGREDIENTS)
    }
}