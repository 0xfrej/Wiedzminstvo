package dev.sharpwave.wiedzminstvo.advancements.criterion

import net.minecraft.advancements.CriteriaTriggers

object Criterions {
    val GRINDING = CriteriaTriggers.register(IngredientGrindingTrigger())
    val EFFECT_DISCOVERY = CriteriaTriggers.register(EffectDiscoveryTrigger())
}