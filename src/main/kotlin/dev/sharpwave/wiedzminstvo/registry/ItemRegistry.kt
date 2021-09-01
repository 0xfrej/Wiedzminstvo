package dev.sharpwave.wiedzminstvo.registry

import dev.sharpwave.wiedzminstvo.alchemy.IngredientEffect.Model

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.alchemy.IngredientEffect
import dev.sharpwave.wiedzminstvo.alchemy.PotionTiers
import dev.sharpwave.wiedzminstvo.alchemy.IngredientEffect.Model as InEffect
import dev.sharpwave.wiedzminstvo.item.*
import dev.sharpwave.wiedzminstvo.item.PotionItem
import net.minecraft.block.Block
import net.minecraft.item.*
import net.minecraft.potion.Effects
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.eventbus.KotlinEventBus
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object ItemRegistry : IForgeRegistry {
    private val ITEMS: KDeferredRegister<Item> = KDeferredRegister(ForgeRegistries.ITEMS, WiedzminstvoMod.MODID)

    override fun register(bus: KotlinEventBus) {
        ITEMS.register(bus)
    }

    val ALCHEMY_TABLE by ITEMS.registerObject("alchemy_table") { block(BlockRegistry.ALCHEMY_TABLE, ModItemGroup.TAB_ALCHEMY) }
    val MORTAR by ITEMS.registerObject("mortar") { block(BlockRegistry.MORTAR, ModItemGroup.TAB_ALCHEMY) }
    val ALCHEMY_POTION by ITEMS.registerObject("alchemy_potion") { PotionItem() }
    val DRAUGHT_OF_ORIGIN by ITEMS.registerObject("draught_of_origin") { FusionIngredientItem(PotionTiers.BASE) }
    val DRAUGHT_OF_ANCESTORS by ITEMS.registerObject("draught_of_ancestors") { FusionIngredientItem(PotionTiers.MINOR_STRENGTH) }
    val BREW_OF_TRANQUILITY by ITEMS.registerObject("brew_of_tranquility") { FusionIngredientItem(PotionTiers.MINOR_DURATION) }
    val FLASK_OF_ANCIENT_SECRETS by ITEMS.registerObject("flask_of_ancient_secrets") { FusionIngredientItem(PotionTiers.MAJOR_STRENGTH) }
    val DRAUGHT_OF_MASTER by ITEMS.registerObject("draught_of_master") { FusionIngredientItem(PotionTiers.MAJOR_DURATION) }
    val PHILTER_OF_ENDLESS_TIME by ITEMS.registerObject("philter_of_endless_time") { FusionIngredientItem(PotionTiers.MAGIC_DURATION) }
    val FLASK_OF_AWAKENING by ITEMS.registerObject("flask_of_awakening") { FusionIngredientItem(PotionTiers.MAGIC_STRENGTH) }
    val ALCHEMY_PASTE by ITEMS.registerObject("alchemy_paste") { item(ModItemGroup.TAB_ALCHEMY) }
    val SUSPICIOUS_ALCHEMY_PASTE by ITEMS.registerObject("suspicious_alchemy_paste") { SuspiciousAlchemyPaste() }
    val DARK_ESSENCE by ITEMS.registerObject("dark_essence") { item(ModItemGroup.TAB_ALCHEMY) }
    val FIFTH_ESSENCE by ITEMS.registerObject("fifth_essence") { item(ModItemGroup.TAB_ALCHEMY) }
    val ARENARIA by ITEMS.registerObject("arenaria") {
        ingredientBlock(BlockRegistry.ARENARIA, listOf(
            InEffect(Effects.REGENERATION, 3600, 1)
        ))
    }
    val BEGGARTICK by ITEMS.registerObject("beggartick_blossoms") { ingredientBlock(BlockRegistry.BEGGARTICK, emptyList()) }
    val BISON_GRASS by ITEMS.registerObject("bison_grass") { ingredientBlock(BlockRegistry.BISON_GRASS, emptyList()) }
    val BLUE_LOTUS by ITEMS.registerObject("blue_lotus") { tallIngredientBlock(BlockRegistry.BLUE_LOTUS,emptyList()) }
    val WINTER_CHERRY by ITEMS.registerObject("winter_cherry") { tallIngredientBlock(BlockRegistry.WINTER_CHERRY, emptyList()) }
    val FOOLS_PARSLEY by ITEMS.registerObject("fools_parsley") { ingredientBlock(BlockRegistry.FOOLS_PARSLEY, emptyList()) }
    val BERBERCANE by ITEMS.registerObject("berbercane") { ingredientBlock(BlockRegistry.BERBERCANE, emptyList()) }
    val CELANDINE by ITEMS.registerObject("celandine") { ingredientBlock(BlockRegistry.CELANDINE, emptyList()) }
    val CORTINARIUS by ITEMS.registerObject("cortinarius") { ingredientBlock(BlockRegistry.CORTINARIUS, emptyList()) }
    val BALLISEFRUIT by ITEMS.registerObject("ballisefruit") { ingredientBlock(BlockRegistry.BALLISEFRUIT, emptyList()) }
    val GROUND_ARENARIA by ITEMS.registerObject("ground_arenaria") { item(ModItemGroup.TAB_ALCHEMY) }
    val GROUND_BEGGARTICK by ITEMS.registerObject("ground_beggartick") { item(ModItemGroup.TAB_ALCHEMY) }
    val GROUND_BISON_GRASS by ITEMS.registerObject("ground_bison_grass") { item(ModItemGroup.TAB_ALCHEMY) }
    val GROUND_BLUE_LOTUS by ITEMS.registerObject("ground_blue_lotus") { item(ModItemGroup.TAB_ALCHEMY) }
    val GROUND_WINTER_CHERRY by ITEMS.registerObject("ground_winter_cherry") { item(ModItemGroup.TAB_ALCHEMY) }
    val GROUND_FOOLS_PARSLEY by ITEMS.registerObject("ground_fools_parsley") { item(ModItemGroup.TAB_ALCHEMY) }
    val GROUND_BERBERCANE by ITEMS.registerObject("ground_berbercane") { item(ModItemGroup.TAB_ALCHEMY) }
    val GROUND_CELANDINE by ITEMS.registerObject("ground_celandine") { item(ModItemGroup.TAB_ALCHEMY) }
    val PORK_FAT by ITEMS.registerObject("pork_fat") { Item(Item.Properties().tab(ModItemGroup.TAB_MISC).food(Foods.TROPICAL_FISH)) }

    private fun block(block: Block, tab: ItemGroup): BlockItem {
        return BlockItem(block, Item.Properties().tab(tab))
    }

    private fun ingredient(effects: List<Model>, properties: Item.Properties = Item.Properties().tab(ModItemGroup.TAB_ALCHEMY)): AlchemyItem {
        return AlchemyItem(properties, effects)
    }

    private fun ingredientBlock(block: Block, effects: List<Model>, properties: Item.Properties = Item.Properties().tab(ModItemGroup.TAB_ALCHEMY)): AlchemyBlockItem {
        return AlchemyBlockItem(block, properties, effects)
    }

    private fun tallIngredientBlock(block: Block, effects: List<Model>, properties: Item.Properties = Item.Properties().tab(ModItemGroup.TAB_ALCHEMY)): AlchemyTallBlockItem {
        return AlchemyTallBlockItem(block, properties, effects)
    }

    private fun tallBlock(block: Block, tab: ItemGroup): BlockItem {
        return TallBlockItem(block, Item.Properties().tab(tab))
    }

    private fun item(tab: ItemGroup): Item {
        return Item(Item.Properties().tab(tab))
    }
}