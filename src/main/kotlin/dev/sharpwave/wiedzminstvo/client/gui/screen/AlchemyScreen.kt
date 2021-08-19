package dev.sharpwave.wiedzminstvo.client.gui.screen

import com.google.common.collect.Lists
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.renderer.Tessellator
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.EnchantmentNameParts
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.vector.Matrix4f
import net.minecraft.util.math.vector.Vector3f
import net.minecraft.util.text.*
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import java.util.*
// TODO: write screen

/*
@OnlyIn(Dist.CLIENT)
class AlchemyScreen(container: AlchemyContainer, playerInventory: PlayerInventory, title: ITextComponent) : ContainerScreen<AlchemyContainer>(container, playerInventory, title) {
    private var open = 0f
    private var oOpen = 0f

    override fun mouseClicked(x: Double, y: Double, key: Int): Boolean {
        val i = (width - imageWidth) / 2
        val j = (height - imageHeight) / 2
        for (k in 0..2) {
            val d0 = x - (i + 60).toDouble()
            val d1 = y - (j + 14 + 19 * k).toDouble()
            if (d0 >= 0.0 && d1 >= 0.0 && d0 < 108.0 && d1 < 19.0 && menu!!.clickMenuButton(minecraft!!.player!!, k)) {
                minecraft!!.gameMode!!.handleInventoryButtonClick(menu.containerId, k)
                return true
            }
        }
        return super.mouseClicked(x, y, key)
    }

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
        val l = menu.goldCount
        for (i1 in 0..2) {
            val j1 = i + 60
            val k1 = j1 + 20
            blitOffset = 0
            minecraft!!.getTextureManager().bind(ALCHEMY_TABLE_LOCATION)
            val l1 = menu.costs[i1]
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f)
            if (l1 == 0) {
                this.blit(matrixStack, j1, j + 14 + 19 * i1, 0, 185, 108, 19)
            } else {
                val s = "" + l1
                val i2 = 86 - font.width(s)
                val itextproperties = EnchantmentNameParts.getInstance().getRandomName(font, i2)
                var j2 = 6839882
                if ((l < i1 + 1 || minecraft!!.player!!.experienceLevel < l1) && !minecraft!!.player!!.abilities.instabuild || menu.enchantClue[i1] == -1) { // Forge: render buttons as disabled when enchantable but enchantability not met on lower levels
                    this.blit(matrixStack, j1, j + 14 + 19 * i1, 0, 185, 108, 19)
                    this.blit(matrixStack, j1 + 1, j + 15 + 19 * i1, 16 * i1, 239, 16, 16)
                    font.drawWordWrap(itextproperties, k1, j + 16 + 19 * i1, i2, j2 and 16711422 shr 1)
                    j2 = 4226832
                } else {
                    val k2 = p_230450_3_ - (i + 60)
                    val l2 = p_230450_4_ - (j + 14 + 19 * i1)
                    if (k2 >= 0 && l2 >= 0 && k2 < 108 && l2 < 19) {
                        this.blit(matrixStack, j1, j + 14 + 19 * i1, 0, 204, 108, 19)
                        j2 = 16777088
                    } else {
                        this.blit(matrixStack, j1, j + 14 + 19 * i1, 0, 166, 108, 19)
                    }
                    this.blit(matrixStack, j1 + 1, j + 15 + 19 * i1, 16 * i1, 223, 16, 16)
                    font.drawWordWrap(itextproperties, k1, j + 16 + 19 * i1, i2, j2)
                    j2 = 8453920
                }
                font.drawShadow(
                    matrixStack,
                    s,
                    (k1 + 86 - font.width(s)).toFloat(),
                    (j + 16 + 19 * i1 + 7).toFloat(),
                    j2
                )
            }
        }
    }

    override fun render(p_230430_1_: MatrixStack, p_230430_2_: Int, p_230430_3_: Int, p_230430_4_: Float) {
        var p_230430_4_ = p_230430_4_
        p_230430_4_ = minecraft!!.frameTime
        this.renderBackground(p_230430_1_)
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_)
        this.renderTooltip(p_230430_1_, p_230430_2_, p_230430_3_)
        val flag = minecraft!!.player!!.abilities.instabuild
        val i = menu!!.goldCount
        for (j in 0..2) {
            val k = menu.costs[j]
            val enchantment = Enchantment.byId(menu.enchantClue[j])
            val l = menu.levelClue[j]
            val i1 = j + 1
            if (this.isHovering(60, 14 + 19 * j, 108, 17, p_230430_2_.toDouble(), p_230430_3_.toDouble()) && k > 0) {
                val list: MutableList<ITextComponent> = Lists.newArrayList()
                list.add(
                    TranslationTextComponent(
                        "container.enchant.clue",
                        if (enchantment == null) "" else enchantment.getFullname(l)
                    ).withStyle(TextFormatting.WHITE)
                )
                if (enchantment == null) {
                    list.add(StringTextComponent(""))
                    list.add(
                        TranslationTextComponent("forge.container.enchant.limitedEnchantability").withStyle(
                            TextFormatting.RED
                        )
                    )
                } else if (!flag) {
                    list.add(StringTextComponent.EMPTY)
                    if (minecraft!!.player!!.experienceLevel < k) {
                        list.add(
                            TranslationTextComponent(
                                "container.enchant.level.requirement",
                                menu.costs[j]
                            ).withStyle(TextFormatting.RED)
                        )
                    } else {
                        val iformattabletextcomponent: IFormattableTextComponent
                        iformattabletextcomponent = if (i1 == 1) {
                            TranslationTextComponent("container.enchant.lapis.one")
                        } else {
                            TranslationTextComponent("container.enchant.lapis.many", i1)
                        }
                        list.add(iformattabletextcomponent.withStyle(if (i >= i1) TextFormatting.GRAY else TextFormatting.RED))
                        val iformattabletextcomponent1: IFormattableTextComponent
                        iformattabletextcomponent1 = if (i1 == 1) {
                            TranslationTextComponent("container.enchant.level.one")
                        } else {
                            TranslationTextComponent("container.enchant.level.many", i1)
                        }
                        list.add(iformattabletextcomponent1.withStyle(TextFormatting.GRAY))
                    }
                }
                renderComponentTooltip(p_230430_1_, list, p_230430_2_, p_230430_3_)
                break
            }
        }
    }

    companion object {
        private val ALCHEMY_TABLE_LOCATION = ResourceLocation(WiedzminstvoMod.MODID, "textures/gui/container/alchemy_table.png")
    }
}*/