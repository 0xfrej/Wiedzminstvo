package dev.sharpwave.wiedzminstvo.advancements.criterion

import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.alchemy.IAlchemyIngredient
import dev.sharpwave.wiedzminstvo.alchemy.IngredientEffect
import net.minecraft.advancements.criterion.AbstractCriterionTrigger
import net.minecraft.advancements.criterion.CriterionInstance
import net.minecraft.advancements.criterion.EntityPredicate
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.Item
import net.minecraft.loot.ConditionArrayParser
import net.minecraft.loot.ConditionArraySerializer
import net.minecraft.util.JSONUtils
import net.minecraft.util.ResourceLocation
import net.minecraft.util.registry.Registry


class EffectDiscoveryTrigger : AbstractCriterionTrigger<EffectDiscoveryTrigger.Instance>() {
    override fun getId(): ResourceLocation {
        return ID
    }

    public override fun createInstance(
        jObject: JsonObject,
        predicate: EntityPredicate.AndPredicate,
        conditionsParser: ConditionArrayParser
    ): Instance {
        val itemLocation = ResourceLocation(JSONUtils.getAsString(jObject, "item"))
        val item = Registry.ITEM.getOptional(itemLocation).orElseThrow {
            JsonSyntaxException(
                "Unknown potion '$itemLocation'"
            )
        } as Item
        val effectSlot = enumValues<IngredientEffect.Slot>()[JSONUtils.getAsInt(jObject, "slot", IngredientEffect.Slot.FIRST.ordinal)]
        return Instance(predicate, item, effectSlot)
    }

    fun trigger(player: ServerPlayerEntity, item: Item, effectSlot: IngredientEffect.Slot) {
        this.trigger(
            player
        ) { criterion: Instance ->
            criterion.matches(
                item,
                effectSlot
            )
        }
    }

    fun triggerRandom(player: ServerPlayerEntity, ingredient: IAlchemyIngredient) {
        val undiscoveredEffects = ingredient.getUndiscoveredIngredientEffects(player)

        if (! undiscoveredEffects.isEmpty()) {
            val discoveredEffect = if (undiscoveredEffects.size > 1) {
                val random = player.random.nextInt(undiscoveredEffects.size)
                undiscoveredEffects[random]
            } else {
                undiscoveredEffects.first()
            }

            trigger(player, ingredient.item, discoveredEffect)
        }
    }

    class Instance(predicate: EntityPredicate.AndPredicate, private val item: Item, private val effectSlot: IngredientEffect.Slot) :
        CriterionInstance(ID, predicate) {

        fun matches(item: Item, effectSlot: IngredientEffect.Slot): Boolean {
            return this.item === item && this.effectSlot === effectSlot
        }

        override fun serializeToJson(conditionSerializer: ConditionArraySerializer): JsonObject {
            val jObject = super.serializeToJson(conditionSerializer)
            jObject.addProperty("item", Registry.ITEM.getKey(item).toString())
            jObject.addProperty("slot", effectSlot.ordinal)
            return jObject
        }

        companion object {
            fun effectDiscovered(item: Item, effectSlot: IngredientEffect.Slot): Instance {
                return Instance(EntityPredicate.AndPredicate.ANY, item, effectSlot)
            }
        }
    }

    companion object {
        private val ID = ResourceLocation(WiedzminstvoMod.MODID, "effect_discovery")
    }
}