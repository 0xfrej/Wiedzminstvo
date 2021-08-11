package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.registries.ItemRegistry
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.handheldItem
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.itemLocation
import net.minecraft.data.DataGenerator
import net.minecraft.item.Item
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.common.data.ExistingFileHelper

class Items(generator: DataGenerator, existingFileHelper: ExistingFileHelper) : ItemModelProvider(generator, WiedzminstvoMod.MODID, existingFileHelper) {
    private fun getRegistryPath(item: Item): String {
        return item.registryName!!.path;
    }

    override fun registerModels() {
        singleTexture(getRegistryPath(ItemRegistry.AETHER), handheldItem(), "layer0", itemLocation("aether"))
        singleTexture(getRegistryPath(ItemRegistry.ALCHEMISTS_POWDER), handheldItem(), "layer0", itemLocation("alchemists_powder"))
        singleTexture(getRegistryPath(ItemRegistry.ALCHEMY_PASTE), handheldItem(), "layer0", itemLocation("alchemy_paste"))
        singleTexture(getRegistryPath(ItemRegistry.ALCOHEST), handheldItem(), "layer0", itemLocation("alcohest"))
        singleTexture(getRegistryPath(ItemRegistry.BOTTOMLESS_CARAFE), handheldItem(), "layer0", itemLocation("bottomless_carafe"))
        singleTexture(getRegistryPath(ItemRegistry.CALCIUM_EQUUM), handheldItem(), "layer0", itemLocation("calcium_equum"))
        singleTexture(getRegistryPath(ItemRegistry.DARK_ESSENCE), handheldItem(), "layer0", itemLocation("dark_essence"))
        singleTexture(getRegistryPath(ItemRegistry.DWARVEN_SPIRIT), handheldItem(), "layer0", itemLocation("dwarven_spirit"))
        singleTexture(getRegistryPath(ItemRegistry.FIFTH_ESSENCE), handheldItem(), "layer0", itemLocation("fifth_essence"))
        singleTexture(getRegistryPath(ItemRegistry.HYDRAGENUM), handheldItem(), "layer0", itemLocation("hydragenum"))
        singleTexture(getRegistryPath(ItemRegistry.OPTIMA_MATER), handheldItem(), "layer0", itemLocation("optima_mater"))
        singleTexture(getRegistryPath(ItemRegistry.PHOSPHORUS), handheldItem(), "layer0", itemLocation("phosphorus"))
        singleTexture(getRegistryPath(ItemRegistry.QUEBRITH), handheldItem(), "layer0", itemLocation("quebrith"))
        singleTexture(getRegistryPath(ItemRegistry.REBIS), handheldItem(), "layer0", itemLocation("rebis"))
        singleTexture(getRegistryPath(ItemRegistry.RUBEDO), handheldItem(), "layer0", itemLocation("rubedo"))
        singleTexture(getRegistryPath(ItemRegistry.SALTPETER), handheldItem(), "layer0", itemLocation("saltpeter"))
        singleTexture(getRegistryPath(ItemRegistry.SULFUR), handheldItem(), "layer0", itemLocation("sulfur"))
        singleTexture(getRegistryPath(ItemRegistry.VERMILION), handheldItem(), "layer0", itemLocation("vermilion"))
        singleTexture(getRegistryPath(ItemRegistry.VITRIOL), handheldItem(), "layer0", itemLocation("vitriol"))
    }
}