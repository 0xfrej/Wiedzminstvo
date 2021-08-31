package dev.sharpwave.wiedzminstvo.block

import net.minecraft.block.BlockState
import net.minecraft.tags.BlockTags
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorldReader
import net.minecraft.world.server.ServerWorld
import java.util.*

class AlchemyMushroomBlock(properties: Properties) : AlchemyFlowerBlock(properties) {

    override fun getShape(
        state: BlockState,
        blockReader: IBlockReader,
        pos: BlockPos,
        selectionCtx: ISelectionContext
    ): VoxelShape {
        return SHAPE
    }

    override fun randomTick(
        state: BlockState,
        level: ServerWorld,
        pos: BlockPos,
        random: Random
    ) {
        var posShadow = pos
        if (random.nextInt(25) == 0) {
            var i = 5
            for (blockPos in BlockPos.betweenClosed(posShadow.offset(-4, -1, -4), posShadow.offset(4, 1, 4))) {
                if (level.getBlockState(blockPos).`is`(this)) {
                    --i
                    if (i <= 0) {
                        return
                    }
                }
            }
            var newBlockPos = posShadow.offset(
                random.nextInt(3) - 1,
                random.nextInt(2) - random.nextInt(2),
                random.nextInt(3) - 1
            )
            for (k in 0..3) {
                if (level.isEmptyBlock(newBlockPos) && state.canSurvive(level, newBlockPos)) {
                    posShadow = newBlockPos
                }
                newBlockPos = posShadow.offset(
                    random.nextInt(3) - 1,
                    random.nextInt(2) - random.nextInt(2),
                    random.nextInt(3) - 1
                )
            }
            if (level.isEmptyBlock(newBlockPos) && state.canSurvive(level, newBlockPos)) {
                level.setBlock(newBlockPos, state, 2)
            }
        }
    }

    override fun mayPlaceOn(sate: BlockState, reader: IBlockReader, pos: BlockPos): Boolean {
        return sate.isSolidRender(reader, pos)
    }

    override fun canSurvive(state: BlockState, reader: IWorldReader, pos: BlockPos): Boolean {
        val belowPos = pos.below()
        val blockStateBelow = reader.getBlockState(belowPos)
        return if (blockStateBelow.`is`(BlockTags.MUSHROOM_GROW_BLOCK)) {
            true
        } else {
            reader.getRawBrightness(pos, 0) < 13 && blockStateBelow.canSustainPlant(
                reader,
                belowPos,
                Direction.UP,
                this
            )
        }
    }

    companion object {
        val SHAPE: VoxelShape = box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0)
    }
}