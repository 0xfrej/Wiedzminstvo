package dev.sharpwave.wiedzminstvo.block

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import java.util.*
import net.minecraft.block.TallFlowerBlock as BaseTallFlowerBlock

open class TallFlowerBlock(properties: Properties) : BaseTallFlowerBlock(properties) {
    override fun isValidBonemealTarget(reader: IBlockReader, pos: BlockPos, state: BlockState, flag: Boolean): Boolean {
        return false
    }

    override fun isBonemealSuccess(level: World, random: Random, pos: BlockPos, state: BlockState): Boolean {
        return false
    }

    override fun performBonemeal(level: ServerWorld, random: Random, pos: BlockPos, state: BlockState) {}
}