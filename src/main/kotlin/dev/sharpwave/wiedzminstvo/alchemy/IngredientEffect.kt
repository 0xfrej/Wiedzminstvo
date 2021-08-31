package dev.sharpwave.wiedzminstvo.alchemy

import dev.sharpwave.wiedzminstvo.utils.AlchemyHelpers.ingredientEffectLocation
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.Item
import net.minecraft.potion.Effect
import net.minecraft.potion.EffectInstance

class IngredientEffect(
    private val parent: Item,
    val effect: Effect,
    val slot: Slot,
    val baseDuration: Int,
    val baseAmplifier: Int,
    val isAmbient: Boolean = false
) {
    val descriptionId: String
        get() = effect.descriptionId

    fun isDiscovered(player: ServerPlayerEntity): Boolean {
        return if (player.level.isClientSide) {
            false
        } else {
            player.level.server!!.advancements.getAdvancement(ingredientEffectLocation(parent, this))?.let {
                player.advancements.getOrStartProgress(it).isDone
            }
            false
        }
    }

    fun asEffectInstance(tier: IPotionTier) : EffectInstance {
        return EffectInstance(effect, tier.amplifyDuration(baseDuration), tier.amplifyStrength(baseAmplifier), isAmbient, true)
    }

    companion object {
        fun of(effect: Effect, slot: Slot, baseDuration: Int, baseAmplifier: Int, isAmbient: Boolean): Model {
            return Model(effect, slot, baseDuration, baseAmplifier, isAmbient)
        }

        fun of(model: Model, parent: Item): IngredientEffect {
            return IngredientEffect(parent, model.effect, model.slot, model.baseDuration, model.baseAmplifier, model.isAmbient)
        }
    }

    enum class Slot {
        FIRST,
        SECOND,
        THIRD,
        FOURTH
    }

    data class Model(
        val effect: Effect,
        val slot: Slot,
        val baseDuration: Int,
        val baseAmplifier: Int,
        val isAmbient: Boolean = false
    )
}