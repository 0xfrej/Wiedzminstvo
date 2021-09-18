package dev.sharpwave.wiedzminstvo.block.mixins

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.state.IntegerProperty
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorldReader
import net.minecraft.world.server.ServerWorld
import java.util.*

interface IGrowableFlower {
    fun withGrowableFlower(parent: Block)
    fun getGrowableFlower(): IGrowableFlower
    fun getMaxAge(): Int
    fun getAgeProperty(): IntegerProperty
    fun getAge(state: BlockState): Int
    fun isMaxAge(state: BlockState): Boolean
    fun shouldTickRandomly(state: BlockState): Boolean
    fun getStateForAge(age: Int): BlockState
    fun processRandomTick(state: BlockState, level: ServerWorld, pos: BlockPos, random: Random)
    fun canSurviveIn(state: BlockState, reader: IWorldReader, pos: BlockPos): Boolean
}