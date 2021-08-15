package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.registries.ItemRegistry
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
        singleTexture(getRegistryPath(ItemRegistry.AETHER), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.AETHER)))
        singleTexture(getRegistryPath(ItemRegistry.ALCHEMISTS_POWDER), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.ALCHEMISTS_POWDER)))
        singleTexture(getRegistryPath(ItemRegistry.ALCHEMY_PASTE), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.ALCHEMY_PASTE)))
        singleTexture(getRegistryPath(ItemRegistry.ALCOHEST), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.ALCOHEST)))
        singleTexture(getRegistryPath(ItemRegistry.DARK_ESSENCE), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.DARK_ESSENCE)))
        singleTexture(getRegistryPath(ItemRegistry.DWARVEN_SPIRIT), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.DWARVEN_SPIRIT)))
        singleTexture(getRegistryPath(ItemRegistry.FIFTH_ESSENCE), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.FIFTH_ESSENCE)))
        singleTexture(getRegistryPath(ItemRegistry.ARENARIA), generatedItem(), "layer0", blockLocation(getRegistryPath(ItemRegistry.ARENARIA)))
        singleTexture(getRegistryPath(ItemRegistry.BEGGARTICK), generatedItem(), "layer0", blockLocation(getRegistryPath(ItemRegistry.BEGGARTICK)))
        singleTexture(getRegistryPath(ItemRegistry.BISON_GRASS), generatedItem(), "layer0", blockLocation(getRegistryPath(ItemRegistry.BISON_GRASS)))
        singleTexture(getRegistryPath(ItemRegistry.BLUE_LOTUS), generatedItem(), "layer0", tallBlockLocation(getRegistryPath(ItemRegistry.BLUE_LOTUS)))
        singleTexture(getRegistryPath(ItemRegistry.WINTER_CHERRY), generatedItem(), "layer0", tallBlockLocation(getRegistryPath(ItemRegistry.WINTER_CHERRY)))
    }
}