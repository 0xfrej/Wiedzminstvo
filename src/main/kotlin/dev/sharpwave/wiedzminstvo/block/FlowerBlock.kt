package dev.sharpwave.wiedzminstvo.block

import net.minecraft.block.*
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.world.IBlockReader

open class FlowerBlock(properties: Properties) : BushBlock(properties) {
    override fun getShape(
        state: BlockState,
        blockReader: IBlockReader,
        pos: BlockPos,
        selectionCtx: ISelectionContext
    ): VoxelShape {
        val offsetPos = state.getOffset(blockReader, pos)
        return SHAPE.move(offsetPos.x, offsetPos.y, offsetPos.z)
    }

    override fun getOffsetType(): OffsetType {
        return OffsetType.XZ
    }

    companion object {
        private val SHAPE = box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0)
    }
}