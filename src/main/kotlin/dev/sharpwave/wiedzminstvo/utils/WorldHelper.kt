package dev.sharpwave.wiedzminstvo.utils

import net.minecraft.entity.item.ItemEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object WorldHelper {
    fun spawnItemStack(level: World, pos: BlockPos, itemStack: ItemStack) {
        val item = ItemEntity(level, pos.x + .5, pos.y + .5, pos.z + .5, itemStack)
        item.lerpMotion(.0, .1, .0)
        level.addFreshEntity(item)
    }
}