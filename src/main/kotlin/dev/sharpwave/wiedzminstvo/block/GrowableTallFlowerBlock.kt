package dev.sharpwave.wiedzminstvo.block

import dev.sharpwave.wiedzminstvo.block.mixins.AbstractGrowableFlower
import dev.sharpwave.wiedzminstvo.block.mixins.IGrowableFlower
import dev.sharpwave.wiedzminstvo.block.mixins.IShearable
import dev.sharpwave.wiedzminstvo.block.mixins.Shearable
import dev.sharpwave.wiedzminstvo.utils.WorldHelper
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.state.IntegerProperty
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.state.properties.DoubleBlockHalf
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.IWorldReader
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import java.util.*


class GrowableTallFlowerBlock(
    properties: Properties,
    conditions: GrowableConditions.Condition
) :
    TallFlowerBlock(properties),
    IGrowableFlower by GrowableFlower(conditions),
    IShearable by Shearable()
{

    init {
        withShearable(this)
        withGrowableFlower(this)
        registerDefaultState(defaultBlockState().setValue(GrowableFlowerBlock.AGE, 0))
    }

    override fun use(
        state: BlockState,
        level: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        trace: BlockRayTraceResult
    ): ActionResultType {
        return if (state.getValue(GrowableFlowerBlock.AGE) == getMaxAge()) {
            val resultStack = harvest(player.getItemInHand(hand))
            WorldHelper.spawnItemStack(level, pos, resultStack)
            state.setValue(GrowableFlowerBlock.AGE, 0)
            ActionResultType.CONSUME
        }
        else
            super.use(state, level, pos, player, hand, trace)
    }

    override fun canSurvive(state: BlockState, reader: IWorldReader, pos: BlockPos): Boolean {
        return canSurviveIn(state, reader, pos)
    }

    override fun isRandomlyTicking(state: BlockState): Boolean {
        return if (state.getValue(HALF) === DoubleBlockHalf.UPPER) {
            shouldTickRandomly(state)
        }
        else {
            false
        }
    }

    override fun randomTick(state: BlockState, level: ServerWorld, pos: BlockPos, random: Random) {
        processRandomTick(state, level, pos, random)
    }

    override fun createBlockStateDefinition(stateContainer: StateContainer.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(stateContainer)
        stateContainer.add(AGE)
    }

    companion object {
        val AGE: IntegerProperty = BlockStateProperties.AGE_3
    }

    class GrowableFlower(conditions: GrowableConditions.Condition) : AbstractGrowableFlower<GrowableFlowerBlock>(conditions) {
        override fun getMaxAge(): Int {
            return 3
        }

        override fun getAgeProperty(): IntegerProperty {
            return GrowableFlowerBlock.AGE
        }

        override fun getStateForAge(age: Int): BlockState {
            return parent.defaultBlockState().setValue(AGE, Integer.valueOf(age))
        }
    }
}