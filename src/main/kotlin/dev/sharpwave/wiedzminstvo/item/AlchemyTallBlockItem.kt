package dev.sharpwave.wiedzminstvo.item

import dev.sharpwave.wiedzminstvo.alchemy.IAlchemyIngredient
import dev.sharpwave.wiedzminstvo.alchemy.IngredientEffect
import net.minecraft.block.Block
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.*
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.text.ITextComponent
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

class AlchemyTallBlockItem(parent: Block, properties: Properties, effectListFactory: (Item) -> List<IngredientEffect>) : BlockItem(parent, properties), IAlchemyIngredient {
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

    @OnlyIn(Dist.CLIENT)
    override fun appendHoverText(
        stack: ItemStack,
        level: World?,
        tooltips: MutableList<ITextComponent>,
        flag: ITooltipFlag
    ) {
        super<BlockItem>.appendHoverText(stack, level, tooltips, flag)
        super<IAlchemyIngredient>.appendHoverText(stack, level, tooltips, flag)
    }
}