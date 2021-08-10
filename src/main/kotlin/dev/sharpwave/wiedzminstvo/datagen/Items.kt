package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.registries.ItemRegistry
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.handheldItem
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.itemLocation
import net.minecraft.data.DataGenerator
import net.minecraft.item.Item
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.fml.RegistryObject

class Items(generator: DataGenerator, existingFileHelper: ExistingFileHelper) : ItemModelProvider(generator, WiedzminstvoMod.MODID, existingFileHelper) {
    private fun <T:Item> getRegistryPath(item: RegistryObject<T>): String {
        return item.get().registryName!!.path;
    }

    override fun registerModels() {
        singleTexture(getRegistryPath(ItemRegistry.AETHER), handheldItem(), "layer0", itemLocation(ItemRegistry.AETHER.id))
        singleTexture(getRegistryPath(ItemRegistry.ALCHEMISTS_POWDER), handheldItem(), "layer0", itemLocation(ItemRegistry.ALCHEMISTS_POWDER.id))
        singleTexture(getRegistryPath(ItemRegistry.ALCHEMY_PASTE), handheldItem(), "layer0", itemLocation(ItemRegistry.ALCHEMY_PASTE.id))
        singleTexture(getRegistryPath(ItemRegistry.ALCOHEST), handheldItem(), "layer0", itemLocation(ItemRegistry.ALCOHEST.id))
        singleTexture(getRegistryPath(ItemRegistry.BOTTOMLESS_CARAFE), handheldItem(), "layer0", itemLocation(ItemRegistry.BOTTOMLESS_CARAFE.id))
        singleTexture(getRegistryPath(ItemRegistry.CALCIUM_EQUUM), handheldItem(), "layer0", itemLocation(ItemRegistry.CALCIUM_EQUUM.id))
        singleTexture(getRegistryPath(ItemRegistry.DARK_ESSENCE), handheldItem(), "layer0", itemLocation(ItemRegistry.DARK_ESSENCE.id))
        singleTexture(getRegistryPath(ItemRegistry.DWARVEN_SPIRIT), handheldItem(), "layer0", itemLocation(ItemRegistry.DWARVEN_SPIRIT.id))
        singleTexture(getRegistryPath(ItemRegistry.FIFTH_ESSENCE), handheldItem(), "layer0", itemLocation(ItemRegistry.FIFTH_ESSENCE.id))
        singleTexture(getRegistryPath(ItemRegistry.HYDRAGENUM), handheldItem(), "layer0", itemLocation(ItemRegistry.HYDRAGENUM.id))
        singleTexture(getRegistryPath(ItemRegistry.OPTIMA_MATER), handheldItem(), "layer0", itemLocation(ItemRegistry.OPTIMA_MATER.id))
        singleTexture(getRegistryPath(ItemRegistry.PHOSPHORUS), handheldItem(), "layer0", itemLocation(ItemRegistry.PHOSPHORUS.id))
        singleTexture(getRegistryPath(ItemRegistry.QUEBRITH), handheldItem(), "layer0", itemLocation(ItemRegistry.QUEBRITH.id))
        singleTexture(getRegistryPath(ItemRegistry.REBIS), handheldItem(), "layer0", itemLocation(ItemRegistry.REBIS.id))
        singleTexture(getRegistryPath(ItemRegistry.RUBEDO), handheldItem(), "layer0", itemLocation(ItemRegistry.RUBEDO.id))
        singleTexture(getRegistryPath(ItemRegistry.SALTPETER), handheldItem(), "layer0", itemLocation(ItemRegistry.SALTPETER.id))
        singleTexture(getRegistryPath(ItemRegistry.SULFUR), handheldItem(), "layer0", itemLocation(ItemRegistry.SULFUR.id))
        singleTexture(getRegistryPath(ItemRegistry.VERMILLION), handheldItem(), "layer0", itemLocation(ItemRegistry.VERMILLION.id))
        singleTexture(getRegistryPath(ItemRegistry.VITRIOL), handheldItem(), "layer0", itemLocation(ItemRegistry.VITRIOL.id))
    }
}