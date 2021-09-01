package dev.sharpwave.wiedzminstvo.alchemy

import dev.sharpwave.wiedzminstvo.advancements.criterion.Criterions
import dev.sharpwave.wiedzminstvo.locale.GeneralStrings
import dev.sharpwave.wiedzminstvo.utils.AlchemyHelpers.getStyleForIngredientEffect
import dev.sharpwave.wiedzminstvo.utils.AlchemyHelpers.ingredientEffectIsDiscovered
import dev.sharpwave.wiedzminstvo.utils.AlchemyHelpers.ingredientEffectLocation
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.UseAction
import net.minecraft.stats.Stats
import net.minecraft.util.ActionResult
import net.minecraft.util.DrinkHelper
import net.minecraft.util.Hand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.common.extensions.IForgeItem
import javax.swing.Action

interface IAlchemyIngredient: IForgeItem {
    val hasEffects: Boolean
        get() = effects.isNotEmpty()
    val effects: Map<IngredientEffect.Slot, IngredientEffect>

    fun getEffect(slot: IngredientEffect.Slot) : IngredientEffect? {
        return effects[slot]
    }

    fun getEffectCount(): Int {
        return effects.count()
    }

    fun getUndiscoveredIngredientEffects(player: ServerPlayerEntity): List<IngredientEffect.Slot> {
        val list = mutableListOf<IngredientEffect.Slot>()
        for ((slot, element) in effects)
            if (! element.isDiscovered(player))
                list.add(slot)

        return list
    }

    fun finishUsingItem(stack: ItemStack, level: World, entity: LivingEntity): ItemStack {
        val player = if (entity is PlayerEntity) entity else null

        if (player is ServerPlayerEntity) {
            CriteriaTriggers.CONSUME_ITEM.trigger(player, stack)
            Criterions.EFFECT_DISCOVERY.trigger(player, this.item, IngredientEffect.Slot.FIRST)
        }
        if (!level.isClientSide) {
            // Only apply effect if it was not discovered yet
            val effect = effects[IngredientEffect.Slot.FIRST]
            effect?.let {
                if (! effect.isDiscovered(player as ServerPlayerEntity)) {
                    if (it.effect.isInstantenous) {
                        it.effect.applyInstantenousEffect(player, player, entity, 1, 1.0)
                    } else {
                        entity.addEffect(it.asEffectInstance(PotionTiers.BASE))
                    }
                }
            }
        }
        if (player != null) {
            player.awardStat(Stats.ITEM_USED[this.item])
            if (!player.abilities.instabuild) {
                stack.shrink(1)
            }
        }

        return stack
    }

    fun getUseDuration(stack: ItemStack): Int {
        return 32
    }

    fun getUseAnimation(stack: ItemStack): UseAction {
        return UseAction.EAT
    }

    fun use(level: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        return DrinkHelper.useDrink(level, player, hand)
    }

    @OnlyIn(Dist.CLIENT)
    fun appendHoverText(
        stack: ItemStack,
        level: World?,
        tooltips: MutableList<ITextComponent>,
        flag: ITooltipFlag
    ) {
        if (effects.isNotEmpty()) {
            if (Screen.hasShiftDown()) {
                tooltips.add(StringTextComponent.EMPTY)
                val mng = Minecraft.getInstance().player!!.connection.advancements

                for ((_, effect) in effects) {
                    val text = TranslationTextComponent(effect.descriptionId)
                        .withStyle(getStyleForIngredientEffect(
                            effect,
                            ingredientEffectIsDiscovered(mng, this, effect)
                        ))

                    tooltips.add(text)
                }
            }
            else {
                tooltips.add(TranslationTextComponent(GeneralStrings.TOOLTIP_HOLD_SHIFT_FOR_INFO))
            }
        }
    }
}