package dev.sharpwave.wiedzminstvo.item

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.potion.Effect
import net.minecraft.potion.EffectInstance
import net.minecraft.world.World

class SuspiciousAlchemyPaste : Item(Properties().tab(ModItemGroup.TAB_ALCHEMY)) {
    override fun finishUsingItem(stack: ItemStack, levle: World, entity: LivingEntity): ItemStack {
        val resultItemStack = super.finishUsingItem(stack, levle, entity)
        val tag = stack.tag

        if (tag != null && tag.contains("Effects", 9)) {
            val effectList = tag.getList("Effects", 10)

            for (i in effectList.indices) {
                var effectDuration = 160
                val effectTag = effectList.getCompound(i)
                if (effectTag.contains("EffectDuration", 3)) {
                    effectDuration = effectTag.getInt("EffectDuration")
                }
                val effect = Effect.byId(effectTag.getByte("EffectId").toInt())
                if (effect != null) {
                    entity.addEffect(EffectInstance(effect, effectDuration))
                }
            }
        }
        return if (entity is PlayerEntity && entity.abilities.instabuild) resultItemStack else ItemStack.EMPTY
    }

    companion object {
        fun saveMobEffect(stack: ItemStack, effect: Effect, duration: Int) {
            val tag = stack.orCreateTag
            val effectList = tag.getList("Effects", 9)
            val effectTag = CompoundNBT()
            effectTag.putByte("EffectId", Effect.getId(effect).toByte())
            effectTag.putInt("EffectDuration", duration)
            effectList.add(effectTag)
            tag.put("Effects", effectList)
        }
    }
}