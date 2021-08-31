package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.advancements.criterion.EffectDiscoveryTrigger
import dev.sharpwave.wiedzminstvo.advancements.criterion.IngredientGrindingTrigger
import dev.sharpwave.wiedzminstvo.alchemy.IAlchemyIngredient
import dev.sharpwave.wiedzminstvo.datagen.support.AbstractAdvancementProvider
import dev.sharpwave.wiedzminstvo.locale.AlchemyStrings
import dev.sharpwave.wiedzminstvo.registry.BlockRegistry
import dev.sharpwave.wiedzminstvo.registry.ItemRegistry
import dev.sharpwave.wiedzminstvo.utils.AlchemyHelpers.ingredientEffectLocationStr
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.getRegistryPath
import net.minecraft.advancements.Advancement
import net.minecraft.data.DataGenerator
import net.minecraft.advancements.Advancement.Builder
import net.minecraft.advancements.FrameType
import net.minecraft.advancements.criterion.InventoryChangeTrigger
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Consumer


class Advancements(generator: DataGenerator) : AbstractAdvancementProvider(generator) {
    init {
        // Alchemy
        pushTab { consumer ->
            val alchemyRoot = Builder.advancement().display(
                Items.POTION,
                translate(AlchemyStrings.ALCHEMY_ADV_TAB_NAME),
                translate(AlchemyStrings.ALCHEMY_ADV_TAB_DESC),
                ResourceLocation(WiedzminstvoMod.MODID, "textures/gui/advancements/backgrounds/alchemy.png"),
                FrameType.TASK, false, false, false
            )
            .addCriterion("acquired_alchemy_table", InventoryChangeTrigger.Instance.hasItems(ItemRegistry.ALCHEMY_TABLE))
            .save(consumer, location("alchemy/root"))

            val grindingBranch = Builder.advancement().parent(alchemyRoot).display(
                BlockRegistry.MORTAR,
                translate(AlchemyStrings.ALCHEMY_ADV_GRINDING_NAME),
                translate(AlchemyStrings.ALCHEMY_ADV_GRINDING_DESC),
                null,
                FrameType.TASK, true, false, false
            )
            .addCriterion("acquired_mortar", InventoryChangeTrigger.Instance.hasItems(ItemRegistry.MORTAR))
            .save(consumer,  location("alchemy/grinding"))

            generateGrindingAdvancements(grindingBranch, consumer,
                Pair(ItemRegistry.ARENARIA, ItemRegistry.GROUND_ARENARIA),
                Pair(ItemRegistry.BEGGARTICK, ItemRegistry.GROUND_BEGGARTICK),
                Pair(ItemRegistry.BISON_GRASS, ItemRegistry.GROUND_BISON_GRASS),
                Pair(ItemRegistry.BLUE_LOTUS, ItemRegistry.GROUND_BLUE_LOTUS),
                Pair(ItemRegistry.WINTER_CHERRY, ItemRegistry.GROUND_WINTER_CHERRY),
                Pair(ItemRegistry.BERBERCANE, ItemRegistry.GROUND_BERBERCANE),
                Pair(ItemRegistry.FOOLS_PARSLEY, ItemRegistry.FOOLS_PARSLEY),
                Pair(ItemRegistry.CELANDINE, ItemRegistry.GROUND_CELANDINE),
            )

            generateIngredientDiscoveries(alchemyRoot, consumer)
        }
    }

    private fun generateIngredientDiscoveries(parent: Advancement, consumer: Consumer<Advancement>) {
        for (item in ForgeRegistries.ITEMS) {
            if (item is IAlchemyIngredient && item.hasEffects) {
                for (ingredientEffect in item.effects) {
                    Builder.advancement().parent(parent).display(
                        item,
                        translate(AlchemyStrings.ALCHEMY_ADV_INGREDIENT_EFFECT_DISCOVERY_NAME),
                        translate(AlchemyStrings.ALCHEMY_ADV_INGREDIENT_EFFECT_DSICOVERY_DESC),
                        null,
                        FrameType.TASK, true, false, true
                        )
                        .addCriterion("ingredient_effect_discovered", EffectDiscoveryTrigger.Instance.effectDiscovered(item.asItem(), ingredientEffect.slot))
                        .save(consumer, location(ingredientEffectLocationStr(item, ingredientEffect)))
                }
            }
        }
    }

    private fun generateGrindingAdvancements(parent: Advancement, consumer: Consumer<Advancement>, vararg grindables: Pair<Item, Item>) {
        for ((src, dst) in grindables) {
            Builder.advancement().parent(parent).display(
                    src,
                    translate(AlchemyStrings.ALCHEMY_ADV_GRIND_NAME),
                    translate(AlchemyStrings.ALCHEMY_ADV_GRIND_DESC),
                    null,
                    FrameType.CHALLENGE, true, false, true
                )
                .addCriterion("ground_an_ingredient", IngredientGrindingTrigger.Instance.groundAnIngredient(dst))
                .save(consumer, location("alchemy/ingredient_ground_" + getRegistryPath(src)))
        }
    }

    private fun location(key: String): String {
        return WiedzminstvoMod.MODID+":"+key
    }

    private fun translate(key: String): TranslationTextComponent {
        return TranslationTextComponent(key)
    }
}