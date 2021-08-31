package dev.sharpwave.wiedzminstvo.advancements.criterion

import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.advancements.ICriterionTrigger

object Criterions {
    private val entries = LinkedHashMap<String, ICriterionTrigger<*>>()

    val GRINDING = register(IngredientGrindingTrigger())
    val EFFECT_DISCOVERY = register(EffectDiscoveryTrigger())

    fun <T:ICriterionTrigger<*>> register(instance: T): T {
        entries[instance::class.simpleName!!] = instance
        return instance
    }

    fun register() {
        for (entry in entries) {
            CriteriaTriggers.register(entry.value)
        }
    }
}