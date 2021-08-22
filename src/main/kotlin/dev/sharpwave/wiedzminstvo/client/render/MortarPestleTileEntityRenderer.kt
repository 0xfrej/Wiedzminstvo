package dev.sharpwave.wiedzminstvo.client.render

import com.mojang.blaze3d.matrix.MatrixStack
import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.client.render.entity.model.PestleModel
import dev.sharpwave.wiedzminstvo.tileentity.MortarPestleTileEntity
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.model.RenderMaterial
import net.minecraft.client.renderer.texture.AtlasTexture
import net.minecraft.client.renderer.tileentity.TileEntityRenderer
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.vector.Vector3f
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class MortarPestleTileEntityRenderer(terDispatcher: TileEntityRendererDispatcher) : TileEntityRenderer<MortarPestleTileEntity>(terDispatcher) {
    //private val pestleModel = PestleModel()

    override fun render(entity: MortarPestleTileEntity, partialTicks: Float, matrixStack: MatrixStack, renderTypeBuffer: IRenderTypeBuffer, destroyStage: Int, alpha: Int) {
        matrixStack.pushPose()
        val time = entity.time.toFloat() + partialTicks

        matrixStack.mulPose(Vector3f.YP.rotation(20/time * 360))
       // pestleModel.setupAnim(time, MathHelper.clamp(f4, 0.0f, 1.0f), MathHelper.clamp(f5, 0.0f, 1.0f), f6)
        val iVertexBuilder = PESTLE_LOCATION.buffer(renderTypeBuffer) { p_228634_0_: ResourceLocation -> RenderType.entitySolid(p_228634_0_) }
        //pestleModel.render(matrixStack, iVertexBuilder, destroyStage, alpha, 1.0f, 1.0f, 1.0f, 1.0f)
        matrixStack.popPose()
    }

    companion object {
        val PESTLE_LOCATION = RenderMaterial(AtlasTexture.LOCATION_BLOCKS, ResourceLocation(WiedzminstvoMod.MODID, "entity/pestle"))
    }
}