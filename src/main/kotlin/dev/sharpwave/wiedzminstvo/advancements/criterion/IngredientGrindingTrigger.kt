package dev.sharpwave.wiedzminstvo.advancements.criterion

import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
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


class IngredientGrindingTrigger : AbstractCriterionTrigger<IngredientGrindingTrigger.Instance>() {
    override fun getId(): ResourceLocation {
        return ID
    }

    public override fun createInstance(
        jObject: JsonObject,
        predicate: EntityPredicate.AndPredicate,
        conditionsParser: ConditionArrayParser
    ): Instance {
        var item: Item? = null
        if (jObject.has("item")) {
            val itemLocation = ResourceLocation(JSONUtils.getAsString(jObject, "item"))
            item = Registry.ITEM.getOptional(itemLocation).orElseThrow {
                JsonSyntaxException(
                    "Unknown potion '$itemLocation'"
                )
            } as Item
        }
        return Instance(predicate, item)
    }

    fun trigger(player: ServerPlayerEntity, item: Item) {
        this.trigger(
            player
        ) { criterion: Instance ->
            criterion.matches(
                item
            )
        }
    }

    class Instance(predicate: EntityPredicate.AndPredicate, private val item: Item?) :
        CriterionInstance(ID, predicate) {

        fun matches(item: Item): Boolean {
            return this.item == null || this.item === item
        }

        override fun serializeToJson(conditionSerializer: ConditionArraySerializer): JsonObject {
            val jObject = super.serializeToJson(conditionSerializer)
            if (item != null) {
                jObject.addProperty("item", Registry.ITEM.getKey(item).toString())
            }
            return jObject
        }

        companion object {
            fun groundAnIngredient(item: Item? = null, predicate: EntityPredicate.AndPredicate = EntityPredicate.AndPredicate.ANY): Instance {
                return Instance(predicate, item)
            }
        }
    }

    companion object {
        private val ID = ResourceLocation(WiedzminstvoMod.MODID, "ground_item")
    }
}