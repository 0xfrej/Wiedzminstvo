package dev.sharpwave.wiedzminstvo.block

import net.minecraft.block.BlockState
import net.minecraft.state.IntegerProperty
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorldReader
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.common.extensions.IForgeBlock
import java.util.*

interface IGrowableFlower : IForgeBlock {
    val conditions: GrowableConditions.Condition

    fun getMaxAge(): Int
    fun getAgeProperty(): IntegerProperty

    fun getAge(state: BlockState): Int {
        return state.getValue(getAgeProperty())
    }

    fun isMaxAge(state: BlockState): Boolean {
        return state.getValue(getAgeProperty()) >= getMaxAge()
    }

    fun isRandomlyTicking(state: BlockState): Boolean {
        return ! isMaxAge(state)
    }

    fun getStateForAge(age: Int): BlockState

    // TODO: Tweak the speed
    fun randomTick(state: BlockState, level: ServerWorld, pos: BlockPos, random: Random) {
        if (!level.isAreaLoaded(pos, 1)) return  // Forge: prevent loading unloaded chunks when checking neighbor's light

        if (level.getRawBrightness(pos, 0) >= conditions.minGrowingBrightness) {
            val i = getAge(state)
            if (i < getMaxAge()) {
                val f = getGrowthSpeed(level, pos)
                if (ForgeHooks.onCropsGrowPre(
                        level,
                        pos,
                        state,
                        random.nextInt((25.0f / f).toInt() + 1) == 0
                    )
                ) {
                    level.setBlock(pos, getStateForAge(i + 1), 2)
                    ForgeHooks.onCropsGrowPost(level, pos, state)
                }
            }
        }
    }

    fun canSurvive(state: BlockState, reader: IWorldReader, pos: BlockPos): Boolean {
        return (reader.getRawBrightness(
            pos,
            0
        ) > conditions.minSurvivalBrightness || reader.canSeeSky(pos))
    }

    // TODO: Checkup if this doesn't need rewrite
    fun getGrowthSpeed(reader: IBlockReader, pos: BlockPos): Float {
        var baseSpeed = 1.0f
        val north = pos.north()
        val south = pos.south()
        val west = pos.west()
        val east = pos.east()

        val hasSameNeighbourWestEast =
            block === reader.getBlockState(west).block || block === reader.getBlockState(east).block
        val hasSameNeighbourNorthSouth =
            block === reader.getBlockState(north).block || block === reader.getBlockState(south).block
        if (hasSameNeighbourWestEast && hasSameNeighbourNorthSouth) {
            baseSpeed /= 2.0f
        } else if (
                // has same neighbour diagonally
                block === reader.getBlockState(west.north()).block ||
                block === reader.getBlockState(east.north()).block ||
                block === reader.getBlockState(east.south()).block ||
                block === reader.getBlockState(west.south()).block
            ) {
                baseSpeed /= 2.0f
        }
        return baseSpeed
    }
}