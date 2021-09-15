package dev.sharpwave.wiedzminstvo.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.state.IntegerProperty
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.state.properties.DoubleBlockHalf
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorldReader
import net.minecraft.world.server.ServerWorld
import java.util.*


class GrowableTallFlowerBlock(
    properties: Properties,
    override val conditions: GrowableConditions.Condition
) : TallFlowerBlock(properties), IGrowableFlower {

    init {
        registerDefaultState(defaultBlockState().setValue(GrowableFlowerBlock.AGE, 0))
    }

    override fun canSurvive(state: BlockState, reader: IWorldReader, pos: BlockPos): Boolean {
        return super<IGrowableFlower>.canSurvive(state, reader, pos)
    }

    override fun getMaxAge(): Int {
        return 3
    }

    override fun getAgeProperty(): IntegerProperty {
        return GrowableFlowerBlock.AGE
    }

    override fun getStateForAge(age: Int): BlockState {
        return defaultBlockState().setValue(AGE, Integer.valueOf(age)).setValue(HALF, DoubleBlockHalf.UPPER)
    }

    override fun isRandomlyTicking(state: BlockState): Boolean {
        return if (state.getValue(HALF) === DoubleBlockHalf.UPPER) {
            super<IGrowableFlower>.isRandomlyTicking(state)
        }
        else {
            false
        }
    }

    override fun randomTick(state: BlockState, level: ServerWorld, pos: BlockPos, random: Random) {
        super<IGrowableFlower>.randomTick(state, level, pos, random)
    }

    override fun createBlockStateDefinition(stateContainer: StateContainer.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(stateContainer)
        stateContainer.add(AGE)
    }

    companion object {
        val AGE: IntegerProperty = BlockStateProperties.AGE_3
    }
}