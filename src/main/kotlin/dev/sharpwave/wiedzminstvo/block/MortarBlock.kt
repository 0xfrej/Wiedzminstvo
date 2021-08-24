package dev.sharpwave.wiedzminstvo.block


import dev.sharpwave.wiedzminstvo.registry.RecipeRegistry
import dev.sharpwave.wiedzminstvo.tileentity.MortarPestleTileEntity
import dev.sharpwave.wiedzminstvo.utils.RecipeHelper
import dev.sharpwave.wiedzminstvo.utils.WorldHelper
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.pathfinding.PathType
import net.minecraft.tileentity.TileEntity
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

    override fun getShape(
        state: BlockState,
        reader: IBlockReader,
        pos: BlockPos,
        selection: ISelectionContext
    ): VoxelShape {
        return SHAPE
    }

    override fun hasTileEntity(state: BlockState?): Boolean {
        return true
    }

    override fun createTileEntity(state: BlockState, world: IBlockReader): TileEntity {
        return MortarPestleTileEntity()
    }

    override fun getRenderShape(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }

    override fun use(
        state: BlockState,
        level: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        rayTrace: BlockRayTraceResult
    ): ActionResultType {
        return if (level.isClientSide) {
            ActionResultType.SUCCESS
        } else {
            val te = level.getBlockEntity(pos) as MortarPestleTileEntity
            val handStack = player.getItemInHand(hand)

            if (!te.items.isEmpty and !te.canGrind) {
                if (handStack.isEmpty) {
                    val teStack = te.popItems()
                    player.setItemInHand(hand, teStack)
                } else if (handStack.count < handStack.maxStackSize && handStack.sameItem(te.items)) {
                    handStack.count++
                    player.setItemInHand(hand, handStack)
                    te.popItems()
                } else {
                    te.pushItems(ItemStack.EMPTY)
                }
            } else if (!handStack.isEmpty and RecipeHelper.canCraftFromItem(
                    RecipeRegistry.GRINDING,
                    handStack,
                    level
                )
            ) {
                val newStack = handStack.copy()
                newStack.count = 1
                handStack.count--
                te.pushItems(newStack)
                player.setItemInHand(hand, handStack)
            } else if (player.isCrouching and handStack.isEmpty) {
                val teStack = te.popItems()
                player.setItemInHand(hand, teStack)
            } else if (!te.isGrinding) {
                te.attemptGrinding()
            }

            ActionResultType.CONSUME
        }
    }

    override fun setPlacedBy(level: World, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) {
        if (stack.hasCustomHoverName()) {
            val tileEntity = level.getBlockEntity(pos)
            if (tileEntity is MortarPestleTileEntity) {
                tileEntity.setCustomName(stack.hoverName)
            }
        }
    }

    override fun isPathfindable(state: BlockState, reader: IBlockReader, pos: BlockPos, pathType: PathType): Boolean {
        return false
    }

    override fun onRemove(state: BlockState, level: World, pos: BlockPos, state2: BlockState, flag: Boolean) {
        if (!state.`is`(state2.block)) {
            val tileEntity = level.getBlockEntity(pos)
            if (tileEntity is MortarPestleTileEntity && !tileEntity.items.isEmpty) {
                val teStack = tileEntity.popItems()
                WorldHelper.spawnItemStack(level, pos, teStack)
            }
            super.onRemove(state, level, pos, state2, flag)
        }
    }

    companion object {
        private val SHAPE = box(4.5, 0.0, 4.5, 11.5, 4.0, 11.5)
    }
}