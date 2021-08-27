package dev.sharpwave.wiedzminstvo.item

import dev.sharpwave.wiedzminstvo.alchemy.IAlchemyIngredient
import dev.sharpwave.wiedzminstvo.alchemy.IngredientEffect
import net.minecraft.block.Block
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.UseAction
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.world.World

class AlchemyBlockItem(parent: Block, properties: Properties, effectListFactory: (Item) -> List<IngredientEffect>) : BlockItem(parent, properties), IAlchemyIngredient {
    override val effects: List<IngredientEffect>

    init {
        effects = effectListFactory(this)
    }

    override fun finishUsingItem(stack: ItemStack, level: World, entity: LivingEntity): ItemStack {
        return super<IAlchemyIngredient>.finishUsingItem(stack, level, entity)
    }

    override fun getUseDuration(stack: ItemStack): Int {
        return super<IAlchemyIngredient>.getUseDuration(stack)
    }

    override fun getUseAnimation(stack: ItemStack): UseAction {
        return super<IAlchemyIngredient>.getUseAnimation(stack)
    }

    override fun use(level: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        return super<IAlchemyIngredient>.use(level, player, hand)
    }
}