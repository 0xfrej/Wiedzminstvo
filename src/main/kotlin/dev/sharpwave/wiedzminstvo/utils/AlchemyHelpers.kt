package dev.sharpwave.wiedzminstvo.utils

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.alchemy.IAlchemyIngredient
import dev.sharpwave.wiedzminstvo.alchemy.IngredientEffect
import net.minecraft.client.multiplayer.ClientAdvancementManager
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.Color
import net.minecraft.util.text.Style
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

object AlchemyHelpers {
    fun ingredientEffectLocationStr(ingredient: Item, effect: IngredientEffect): String {
        return "alchemy/ie_" + DatagenHelpers.getRegistryPath(ingredient) + "_" + effect.effect.registryName!!.path
    }

    fun ingredientEffectLocation(ingredient: Item, effect: IngredientEffect): ResourceLocation {
        return ResourceLocation(WiedzminstvoMod.MODID, ingredientEffectLocationStr(ingredient, effect))
    }

    @OnlyIn(Dist.CLIENT)
    fun ingredientEffectIsDiscovered(manager: ClientAdvancementManager, ingredient: IAlchemyIngredient, effect: IngredientEffect): Boolean {
        return manager.progress[manager.advancements.get(ingredientEffectLocation(ingredient.item, effect))]?.isDone ?: false
    }

    fun getStyleForIngredientEffect(ingredientEffect: IngredientEffect, isDiscovered: Boolean): Style {
        return Style.EMPTY.withColor(Color.fromRgb(ingredientEffect.effect.color))
                .setObfuscated(!isDiscovered)
    }
}