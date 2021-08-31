package dev.sharpwave.wiedzminstvo.advancements.criterion

import net.minecraft.advancements.CriteriaTriggers

object Criterions {
    val GRINDING: IngredientGrindingTrigger = CriteriaTriggers.register(IngredientGrindingTrigger())
    val EFFECT_DISCOVERY: EffectDiscoveryTrigger = CriteriaTriggers.register(EffectDiscoveryTrigger())
}