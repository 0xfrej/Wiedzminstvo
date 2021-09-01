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
        tag(ItemTypeTags.ALCHEMY_INGREDIENTS).add(ItemRegistry.BLUE_LOTUS, ItemRegistry.WINTER_CHERRY, ItemRegistry.ARENARIA, ItemRegistry.BEGGARTICK, ItemRegistry.BISON_GRASS, ItemRegistry.BERBERCANE, ItemRegistry.CELANDINE, ItemRegistry.FOOLS_PARSLEY, ItemRegistry.CORTINARIUS)
        tag(ItemTypeTags.GROUND_ALCHEMY_INGREDIENTS).add(ItemRegistry.GROUND_BLUE_LOTUS, ItemRegistry.GROUND_WINTER_CHERRY, ItemRegistry.GROUND_ARENARIA, ItemRegistry.GROUND_BEGGARTICK, ItemRegistry.GROUND_BISON_GRASS, ItemRegistry.GROUND_BERBERCANE, ItemRegistry.GROUND_CELANDINE, ItemRegistry.GROUND_FOOLS_PARSLEY)
        tag(ItemTypeTags.ALCHEMY_FUSION_INGREDIENTS).add(ItemRegistry.ALCHEMY_PASTE, ItemRegistry.DRAUGHT_OF_ORIGIN, ItemRegistry.DRAUGHT_OF_ANCESTORS, ItemRegistry.BREW_OF_TRANQUILITY, ItemRegistry.FLASK_OF_ANCIENT_SECRETS, ItemRegistry.DRAUGHT_OF_MASTER, ItemRegistry.PHILTER_OF_ENDLESS_TIME, ItemRegistry.FLASK_OF_AWAKENING)
    }
}