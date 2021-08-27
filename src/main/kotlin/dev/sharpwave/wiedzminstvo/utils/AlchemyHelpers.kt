package dev.sharpwave.wiedzminstvo.utils

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.alchemy.IngredientEffect
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation

object AlchemyHelpers {
    fun ingredientEffectLocationStr(ingredient: Item, effect: IngredientEffect): String {
        return "alchemy/ie_" + DatagenHelpers.getRegistryPath(ingredient) + "_" + effect.effect.registryName!!.path
    }
    fun ingredientEffectLocation(ingredient: Item, effect: IngredientEffect): ResourceLocation {
        return ResourceLocation(WiedzminstvoMod.MODID, ingredientEffectLocationStr(ingredient, effect))
    }
}