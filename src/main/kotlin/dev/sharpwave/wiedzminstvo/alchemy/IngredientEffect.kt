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
    val isAmbient: Boolean
) {

    enum class Slot {
        FIRST,
        SECOND,
        THIRD,
        FOURTH
    }

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
}