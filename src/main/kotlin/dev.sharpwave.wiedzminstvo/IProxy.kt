package dev.sharpwave.wiedzminstvo

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World


interface IProxy {
    val world: World?
    val player: PlayerEntity?

    fun displayStatViewer()
}