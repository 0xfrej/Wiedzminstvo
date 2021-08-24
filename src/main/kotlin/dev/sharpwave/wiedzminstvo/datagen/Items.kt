package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.registry.ItemRegistry
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.blockLocation
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.generatedItem
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.getRegistryPath
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.handheldItem
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.itemLocation
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.tallBlockLocation
import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.common.data.ExistingFileHelper

class Items(generator: DataGenerator, existingFileHelper: ExistingFileHelper) : ItemModelProvider(generator, WiedzminstvoMod.MODID, existingFileHelper) {
    override fun registerModels() {
        singleTexture(getRegistryPath(ItemRegistry.ALCHEMY_PASTE), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.ALCHEMY_PASTE)))
        singleTexture(getRegistryPath(ItemRegistry.DARK_ESSENCE), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.DARK_ESSENCE)))
        singleTexture(getRegistryPath(ItemRegistry.FIFTH_ESSENCE), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.FIFTH_ESSENCE)))
        singleTexture(getRegistryPath(ItemRegistry.ARENARIA), generatedItem(), "layer0", blockLocation(getRegistryPath(ItemRegistry.ARENARIA)))
        singleTexture(getRegistryPath(ItemRegistry.BEGGARTICK), generatedItem(), "layer0", blockLocation(getRegistryPath(ItemRegistry.BEGGARTICK)))
        singleTexture(getRegistryPath(ItemRegistry.BISON_GRASS), generatedItem(), "layer0", blockLocation(getRegistryPath(ItemRegistry.BISON_GRASS)))
        singleTexture(getRegistryPath(ItemRegistry.BLUE_LOTUS), generatedItem(), "layer0", tallBlockLocation(getRegistryPath(ItemRegistry.BLUE_LOTUS)))
        singleTexture(getRegistryPath(ItemRegistry.WINTER_CHERRY), generatedItem(), "layer0", tallBlockLocation(getRegistryPath(ItemRegistry.WINTER_CHERRY)))
        // TODO: Make textures for ground flowers
        singleTexture(getRegistryPath(ItemRegistry.GROUND_ARENARIA), generatedItem(), "layer0", blockLocation(getRegistryPath(ItemRegistry.ARENARIA)))
        singleTexture(getRegistryPath(ItemRegistry.GROUND_BEGGARTICK), generatedItem(), "layer0", blockLocation(getRegistryPath(ItemRegistry.BEGGARTICK)))
        singleTexture(getRegistryPath(ItemRegistry.GROUND_BISON_GRASS), generatedItem(), "layer0", blockLocation(getRegistryPath(ItemRegistry.BISON_GRASS)))
        singleTexture(getRegistryPath(ItemRegistry.GROUND_BLUE_LOTUS), generatedItem(), "layer0", tallBlockLocation(getRegistryPath(ItemRegistry.BLUE_LOTUS)))
        singleTexture(getRegistryPath(ItemRegistry.GROUND_WINTER_CHERRY), generatedItem(), "layer0", tallBlockLocation(getRegistryPath(ItemRegistry.WINTER_CHERRY)))
        singleTexture(getRegistryPath(ItemRegistry.PORK_FAT), generatedItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.PORK_FAT)))
    }
}