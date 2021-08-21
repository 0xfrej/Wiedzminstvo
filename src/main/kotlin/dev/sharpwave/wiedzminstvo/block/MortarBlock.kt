package dev.sharpwave.wiedzminstvo.block


import dev.sharpwave.wiedzminstvo.tileentity.AlchemyTableTileEntity
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.pathfinding.PathType
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.world.IBlockReader
import net.minecraft.world.World

class MortarBlock(properties: Properties) : Block(properties) {
    override fun useShapeForLightOcclusion(state: BlockState): Boolean {
        return true
    }

    override fun getShape(state: BlockState, reader: IBlockReader, pos: BlockPos, selection: ISelectionContext): VoxelShape {
        return SHAPE
    }

    override fun getRenderShape(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }

    override fun use(state: BlockState, level: World, pos: BlockPos, player: PlayerEntity, hand: Hand, rayTrace: BlockRayTraceResult): ActionResultType {
        return if (level.isClientSide) {
            ActionResultType.SUCCESS
        } else {
            player.openMenu(state.getMenuProvider(level, pos))
            ActionResultType.CONSUME
        }
    }

    override fun setPlacedBy(level: World, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) {
        if (stack.hasCustomHoverName()) {
            val tileEntity = level.getBlockEntity(pos)
            if (tileEntity is AlchemyTableTileEntity) {
                tileEntity.setCustomName(stack.hoverName)
            }
        }
    }

    override fun isPathfindable(state: BlockState, reader: IBlockReader, pos: BlockPos, pathType: PathType): Boolean {
        return false
    }

    companion object {
        // TODO: fix this shape box
        private val SHAPE = box(0.0, 0.0, 0.0, 7.0, 4.0, 7.0)
    }
}