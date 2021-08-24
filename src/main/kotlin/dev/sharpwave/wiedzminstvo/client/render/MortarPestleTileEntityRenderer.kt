package dev.sharpwave.wiedzminstvo.client.render

import com.mojang.blaze3d.matrix.MatrixStack
import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.tileentity.MortarPestleTileEntity
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.model.IBakedModel
import net.minecraft.client.renderer.model.ItemCameraTransforms
import net.minecraft.client.renderer.model.RenderMaterial
import net.minecraft.client.renderer.texture.AtlasTexture
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.client.renderer.tileentity.TileEntityRenderer
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
import net.minecraft.item.BlockItem
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.vector.Vector3f
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.client.model.SimpleModelTransform

@OnlyIn(Dist.CLIENT)
class MortarPestleTileEntityRenderer(terDispatcher: TileEntityRendererDispatcher) : TileEntityRenderer<MortarPestleTileEntity>(terDispatcher) {

    private var pestleModel: IBakedModel? = null

    override fun render(entity: MortarPestleTileEntity, partialTicks: Float, matrixStack: MatrixStack, renderTypeBuffer: IRenderTypeBuffer, packedLightIn: Int, alpha: Int) {
        renderPestle(entity, partialTicks, matrixStack, renderTypeBuffer, packedLightIn, alpha)
        renderItem(entity, matrixStack, renderTypeBuffer, packedLightIn)
    }

    private fun renderPestle(entity: MortarPestleTileEntity, partialTicks: Float, matrixStack: MatrixStack, renderTypeBuffer: IRenderTypeBuffer, packedLightIn: Int, alpha: Int) {
        matrixStack.pushPose()

        matrixStack.translate(0.5, 0.5, 0.5)

        if (entity.time > 0) {
            matrixStack.mulPose(Vector3f.YP.rotationDegrees((360 / 30 * (entity.time + partialTicks)).coerceAtMost(360.0F)))
        }

        matrixStack.translate(-0.5, -0.5, -0.5)

        val vertexBuilder = PESTLE_LOCATION.buffer(renderTypeBuffer) { location: ResourceLocation -> RenderType.entitySolid(location) }
        modelRenderer.renderModel(matrixStack.last(), vertexBuilder, entity.blockState, getPestleModel(), 1.0F, 1.0F, 1.0F, packedLightIn, alpha)

        matrixStack.popPose()
    }

    private fun renderItem(entity: MortarPestleTileEntity, matrixStack: MatrixStack, renderTypeBuffer: IRenderTypeBuffer, packedLightIn: Int) {
        val stack = entity.items
        if (!stack.isEmpty) {
            matrixStack.pushPose()
            matrixStack.translate(0.5, 0.1, 0.5)
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F))
            matrixStack.scale(0.18F, 0.18F, 0.0625F)

            itemRenderer.renderStatic(stack, ItemCameraTransforms.TransformType.NONE, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStack, renderTypeBuffer)
            matrixStack.popPose()
        }
    }

    private fun getPestleModel(): IBakedModel {
        if (pestleModel == null) {
            try {
                // TODO: UV is not correctly mapped (if fixed...remove unneeded pixels from mortar.png
                val model = ModelLoader.instance()!!.getModel(ResourceLocation(WiedzminstvoMod.MODID, "block/pestle"))
                pestleModel = model.bake(
                    ModelLoader.instance()!!,
                    { PESTLE_LOCATION.sprite() },
                    SimpleModelTransform.IDENTITY,
                    ResourceLocation(WiedzminstvoMod.MODID, "block/pestle")
                )
            } catch (e: Exception) {
                throw RuntimeException(e);
            }
        }

        return pestleModel!!
    }

    companion object {
        val itemRenderer = Minecraft.getInstance().itemRenderer
        val modelRenderer = Minecraft.getInstance().blockRenderer.modelRenderer
        val PESTLE_LOCATION = RenderMaterial(AtlasTexture.LOCATION_BLOCKS, ResourceLocation(WiedzminstvoMod.MODID, "block/mortar"))
    }
}