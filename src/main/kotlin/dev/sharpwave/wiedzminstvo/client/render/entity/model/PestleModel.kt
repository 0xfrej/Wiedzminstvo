package dev.sharpwave.wiedzminstvo.client.render.entity.model

import com.google.common.collect.ImmutableList
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.vertex.IVertexBuilder
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.model.Model
import net.minecraft.client.renderer.model.ModelRenderer
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.MathHelper
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import java.util.function.Consumer
import java.util.function.Function


@OnlyIn(Dist.CLIENT)
class PestleModel : Model(Function { resource: ResourceLocation -> RenderType.entitySolid(resource) }) {

    private val leftLid = ModelRenderer(64, 32, 0, 0).addBox(-6.0f, -5.0f, -0.005f, 6.0f, 10.0f, 0.005f)
    private val rightLid = ModelRenderer(64, 32, 16, 0).addBox(0.0f, -5.0f, -0.005f, 6.0f, 10.0f, 0.005f)
    private val leftPages: ModelRenderer
    private val rightPages: ModelRenderer
    private val flipPage1: ModelRenderer
    private val flipPage2: ModelRenderer
    private val seam = ModelRenderer(64, 32, 12, 0).addBox(-1.0f, -5.0f, 0.0f, 2.0f, 10.0f, 0.005f)
    private val parts: List<ModelRenderer>
    override fun renderToBuffer(
        p_225598_1_: MatrixStack,
        p_225598_2_: IVertexBuilder,
        p_225598_3_: Int,
        p_225598_4_: Int,
        p_225598_5_: Float,
        p_225598_6_: Float,
        p_225598_7_: Float,
        p_225598_8_: Float
    ) {
        render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_)
    }

    fun render(
        p_228249_1_: MatrixStack?,
        p_228249_2_: IVertexBuilder?,
        p_228249_3_: Int,
        p_228249_4_: Int,
        p_228249_5_: Float,
        p_228249_6_: Float,
        p_228249_7_: Float,
        p_228249_8_: Float
    ) {
        parts.forEach(Consumer { p_228248_8_: ModelRenderer ->
            p_228248_8_.render(
                p_228249_1_,
                p_228249_2_,
                p_228249_3_,
                p_228249_4_,
                p_228249_5_,
                p_228249_6_,
                p_228249_7_,
                p_228249_8_
            )
        })
    }

    fun setupAnim(p_228247_1_: Float, p_228247_2_: Float, p_228247_3_: Float, p_228247_4_: Float) {
        val f = (MathHelper.sin(p_228247_1_ * 0.02f) * 0.1f + 1.25f) * p_228247_4_
        leftLid.yRot = Math.PI.toFloat() + f
        rightLid.yRot = -f
        leftPages.yRot = f
        rightPages.yRot = -f
        flipPage1.yRot = f - f * 2.0f * p_228247_2_
        flipPage2.yRot = f - f * 2.0f * p_228247_3_
        leftPages.x = MathHelper.sin(f)
        rightPages.x = MathHelper.sin(f)
        flipPage1.x = MathHelper.sin(f)
        flipPage2.x = MathHelper.sin(f)
    }

    init {
        leftPages = ModelRenderer(64, 32, 0, 10).addBox(0.0f, -4.0f, -0.99f, 5.0f, 8.0f, 1.0f)
        rightPages = ModelRenderer(64, 32, 12, 10).addBox(0.0f, -4.0f, -0.01f, 5.0f, 8.0f, 1.0f)
        flipPage1 = ModelRenderer(64, 32, 24, 10).addBox(0.0f, -4.0f, 0.0f, 5.0f, 8.0f, 0.005f)
        flipPage2 = ModelRenderer(64, 32, 24, 10).addBox(0.0f, -4.0f, 0.0f, 5.0f, 8.0f, 0.005f)
        parts = ImmutableList.of(leftLid, rightLid, seam, leftPages, rightPages, flipPage1, flipPage2)
        leftLid.setPos(0.0f, 0.0f, -1.0f)
        rightLid.setPos(0.0f, 0.0f, 1.0f)
        seam.yRot = Math.PI.toFloat() / 2f
    }
}