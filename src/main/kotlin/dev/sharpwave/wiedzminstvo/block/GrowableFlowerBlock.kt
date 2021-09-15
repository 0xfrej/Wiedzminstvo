package dev.sharpwave.wiedzminstvo.block

import net.minecraft.block.*
import net.minecraft.state.IntegerProperty
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorldReader
import net.minecraft.world.server.ServerWorld
import java.util.*

class GrowableFlowerBlock(
    properties: Properties,
    override val conditions: GrowableConditions.Condition
) : FlowerBlock(properties), IGrowableFlower {

    init {
        registerDefaultState(stateDefinition.any().setValue(AGE, 0))
    }

    override fun canSurvive(state: BlockState, reader: IWorldReader, pos: BlockPos): Boolean {
        return super<IGrowableFlower>.canSurvive(state, reader, pos)
    }

    override fun getMaxAge(): Int {
        return 3
    }

    override fun getAgeProperty(): IntegerProperty {
        return AGE
    }

    override fun getStateForAge(age: Int): BlockState {
        return defaultBlockState().setValue(GrowableTallFlowerBlock.AGE, Integer.valueOf(age))
    }

    override fun isRandomlyTicking(state: BlockState): Boolean {
        return super<IGrowableFlower>.isRandomlyTicking(state)
    }

    override fun randomTick(state: BlockState, level: ServerWorld, pos: BlockPos, random: Random) {
        super<IGrowableFlower>.randomTick(state, level, pos, random)
    }

    override fun createBlockStateDefinition(stateContainer: StateContainer.Builder<Block, BlockState>) {
        stateContainer.add(GrowableTallFlowerBlock.AGE)
    }

    companion object {
        val AGE: IntegerProperty = BlockStateProperties.AGE_3
    }
}