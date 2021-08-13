package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.registries.ItemRegistry
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.getRegistryPath
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.handheldItem
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.itemLocation
import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.common.data.ExistingFileHelper

class Items(generator: DataGenerator, existingFileHelper: ExistingFileHelper) : ItemModelProvider(generator, WiedzminstvoMod.MODID, existingFileHelper) {
    override fun registerModels() {
        singleTexture(getRegistryPath(ItemRegistry.AETHER), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.AETHER)))
        singleTexture(getRegistryPath(ItemRegistry.ALCHEMISTS_POWDER), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.ALCHEMISTS_POWDER)))
        singleTexture(getRegistryPath(ItemRegistry.ALCHEMY_PASTE), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.ALCHEMY_PASTE)))
        singleTexture(getRegistryPath(ItemRegistry.ALCOHEST), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.ALCOHEST)))
        singleTexture(getRegistryPath(ItemRegistry.BOTTOMLESS_CARAFE), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.BOTTOMLESS_CARAFE)))
        singleTexture(getRegistryPath(ItemRegistry.CALCIUM_EQUUM), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.CALCIUM_EQUUM)))
        singleTexture(getRegistryPath(ItemRegistry.DARK_ESSENCE), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.DARK_ESSENCE)))
        singleTexture(getRegistryPath(ItemRegistry.DWARVEN_SPIRIT), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.DWARVEN_SPIRIT)))
        singleTexture(getRegistryPath(ItemRegistry.FIFTH_ESSENCE), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.FIFTH_ESSENCE)))
        singleTexture(getRegistryPath(ItemRegistry.HYDRAGENUM), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.HYDRAGENUM)))
        singleTexture(getRegistryPath(ItemRegistry.OPTIMA_MATER), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.OPTIMA_MATER)))
        singleTexture(getRegistryPath(ItemRegistry.PHOSPHORUS), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.PHOSPHORUS)))
        singleTexture(getRegistryPath(ItemRegistry.QUEBRITH), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.QUEBRITH)))
        singleTexture(getRegistryPath(ItemRegistry.REBIS), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.REBIS)))
        singleTexture(getRegistryPath(ItemRegistry.RUBEDO), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.RUBEDO)))
        singleTexture(getRegistryPath(ItemRegistry.SALTPETER), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.SALTPETER)))
        singleTexture(getRegistryPath(ItemRegistry.SULFUR), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.SULFUR)))
        singleTexture(getRegistryPath(ItemRegistry.VERMILION), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.VERMILION)))
        singleTexture(getRegistryPath(ItemRegistry.VITRIOL), handheldItem(), "layer0", itemLocation(getRegistryPath(ItemRegistry.VITRIOL)))
        //singleTexture(getRegistryPath(BlockRegistry.ARENARIA), handheldItem(), "layer0", blockLocation(getRegistryPath(BlockRegistry.ARENARIA)))
        //singleTexture(getRegistryPath(BlockRegistry.BEGGARTICK), handheldItem(), "layer0", blockLocation(getRegistryPath(BlockRegistry.BEGGARTICK)))
    }
}