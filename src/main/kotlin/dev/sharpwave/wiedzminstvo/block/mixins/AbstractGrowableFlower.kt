package dev.sharpwave.wiedzminstvo.block.mixins

import dev.sharpwave.wiedzminstvo.block.GrowableConditions
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.state.IntegerProperty
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorldReader
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.common.ForgeHooks
import java.util.*

abstract class AbstractGrowableFlower <T:Block> (val conditions: GrowableConditions.Condition) : IGrowableFlower {
    protected lateinit var parent: Block

    override fun withGrowableFlower(parent: Block) {
        this.parent = parent
    }

    override fun getGrowableFlower(): IGrowableFlower {
        return this
    }

    abstract override fun getMaxAge(): Int
    abstract override fun getAgeProperty(): IntegerProperty

    override fun getAge(state: BlockState): Int {
        return state.getValue(getAgeProperty())
    }

    override fun isMaxAge(state: BlockState): Boolean {
        return state.getValue(getAgeProperty()) >= getMaxAge()
    }

    override fun shouldTickRandomly(state: BlockState): Boolean {
        return ! isMaxAge(state)
    }

    abstract override fun getStateForAge(age: Int): BlockState

    // TODO: Tweak the speed
    override fun processRandomTick(state: BlockState, level: ServerWorld, pos: BlockPos, random: Random) {
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

    override fun canSurviveIn(state: BlockState, reader: IWorldReader, pos: BlockPos): Boolean {
        return (reader.getRawBrightness(
            pos,
            0
        ) > conditions.minSurvivalBrightness || reader.canSeeSky(pos))
    }

    // TODO: Checkup if this doesn't need rewrite
    protected fun getGrowthSpeed(reader: IBlockReader, pos: BlockPos): Float {
        var baseSpeed = 1.0f
        val north = pos.north()
        val south = pos.south()
        val west = pos.west()
        val east = pos.east()

        val hasSameNeighbourWestEast =
            parent === reader.getBlockState(west).block || parent === reader.getBlockState(east).block
        val hasSameNeighbourNorthSouth =
            parent === reader.getBlockState(north).block || parent === reader.getBlockState(south).block
        if (hasSameNeighbourWestEast && hasSameNeighbourNorthSouth) {
            baseSpeed /= 2.0f
        } else if (
        // has same neighbour diagonally
            parent === reader.getBlockState(west.north()).block ||
            parent === reader.getBlockState(east.north()).block ||
            parent === reader.getBlockState(east.south()).block ||
            parent === reader.getBlockState(west.south()).block
        ) {
            baseSpeed /= 2.0f
        }
        return baseSpeed
    }
}