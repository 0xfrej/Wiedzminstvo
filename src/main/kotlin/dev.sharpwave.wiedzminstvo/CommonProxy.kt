package dev.sharpwave.wiedzminstvo

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World

class CommonProxy : IProxy {
    override val world: World?
        get() = null
    override val player: PlayerEntity?
        get() = null

    override fun displayStatViewer() {}
}