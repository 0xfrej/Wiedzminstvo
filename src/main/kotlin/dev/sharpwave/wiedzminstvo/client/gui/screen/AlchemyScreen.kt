package dev.sharpwave.wiedzminstvo.client.gui.screen

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.inventory.container.AlchemyContainer
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.renderer.Tessellator
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.vector.Matrix4f
import net.minecraft.util.math.vector.Vector3f
import net.minecraft.util.text.ITextComponent
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class AlchemyScreen(container: AlchemyContainer, playerInventory: PlayerInventory, title: ITextComponent) :
    ContainerScreen<AlchemyContainer>(container, playerInventory, title) {
    private var open = 0f
    private var oOpen = 0f

    override fun renderBg(matrixStack: MatrixStack, p_230450_2_: Float, p_230450_3_: Int, p_230450_4_: Int) {
        RenderHelper.setupForFlatItems()
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f)
        minecraft!!.getTextureManager().bind(ALCHEMY_TABLE_LOCATION)
        val i = (width - imageWidth) / 2
        val j = (height - imageHeight) / 2
        this.blit(matrixStack, i, j, 0, 0, imageWidth, imageHeight)
        RenderSystem.matrixMode(5889)
        RenderSystem.pushMatrix()
        RenderSystem.loadIdentity()
        val k = minecraft!!.window.guiScale.toInt()
        RenderSystem.viewport((width - 320) / 2 * k, (height - 240) / 2 * k, 320 * k, 240 * k)
        RenderSystem.translatef(-0.34f, 0.23f, 0.0f)
        RenderSystem.multMatrix(Matrix4f.perspective(90.0, 1.3333334f, 9.0f, 80.0f))
        RenderSystem.matrixMode(5888)
        matrixStack.pushPose()
        val matrixStackForEntry = matrixStack.last()
        matrixStackForEntry.pose().setIdentity()
        matrixStackForEntry.normal().setIdentity()
        matrixStack.translate(0.0, 3.3, 1984.0)
        matrixStack.scale(5.0f, 5.0f, 5.0f)
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180.0f))
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(20.0f))
        val f1 = MathHelper.lerp(p_230450_2_, oOpen, open)
        matrixStack.translate(
            ((1.0f - f1) * 0.2f).toDouble(),
            ((1.0f - f1) * 0.1f).toDouble(),
            ((1.0f - f1) * 0.25f).toDouble()
        )
        val f2 = -(1.0f - f1) * 90.0f - 90.0f
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(f2))
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0f))

        RenderSystem.enableRescaleNormal()
        val renderTypeBuffer = IRenderTypeBuffer.immediate(Tessellator.getInstance().builder)
        renderTypeBuffer.endBatch()
        matrixStack.popPose()
        RenderSystem.matrixMode(5889)
        RenderSystem.viewport(0, 0, minecraft!!.window.width, minecraft!!.window.height)
        RenderSystem.popMatrix()
        RenderSystem.matrixMode(5888)
        RenderHelper.setupFor3DItems()
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f)
    }

    override fun renderLabels(matrixStack: MatrixStack, x: Int, y: Int) {}

    override fun render(matrixStack: MatrixStack, x: Int, y: Int, time: Float) {
        val frameTime = minecraft!!.frameTime
        renderBackground(matrixStack)
        super.render(matrixStack, x, y, frameTime)
        renderTooltip(matrixStack, x, y)
    }

    companion object {
        private val ALCHEMY_TABLE_LOCATION =
            ResourceLocation(WiedzminstvoMod.MODID, "textures/gui/container/alchemy_table.png")
    }
}