package dev.sharpwave.wiedzminstvo

import dev.sharpwave.wiedzminstvo.client.gui.GuiStatViewer
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

class ClientProxy : IProxy {
    override val world: World
        get() = Minecraft.getInstance().level as World
    override val player: PlayerEntity?
        get() = Minecraft.getInstance().player

    override fun displayStatViewer() {
        Logger.log.debug("displayingStatViewer")
        Minecraft.getInstance().setScreen(GuiStatViewer(player!!))
    }
}