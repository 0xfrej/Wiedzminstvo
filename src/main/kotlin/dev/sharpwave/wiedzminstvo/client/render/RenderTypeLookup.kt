package dev.sharpwave.wiedzminstvo.client.render

import dev.sharpwave.wiedzminstvo.registry.BlockRegistry
import net.minecraft.client.renderer.RenderType
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraft.client.renderer.RenderTypeLookup as RTL

@OnlyIn(Dist.CLIENT)
object RenderTypeLookup {
    fun register() {
        val renderCutout = RenderType.cutoutMipped()
        RTL.setRenderLayer(BlockRegistry.ARENARIA, renderCutout)
        RTL.setRenderLayer(BlockRegistry.BEGGARTICK, renderCutout)
        RTL.setRenderLayer(BlockRegistry.BISON_GRASS, renderCutout)
        RTL.setRenderLayer(BlockRegistry.BLUE_LOTUS, renderCutout)
        RTL.setRenderLayer(BlockRegistry.WINTER_CHERRY, renderCutout)
    }
}