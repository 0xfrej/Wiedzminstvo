package dev.sharpwave.wiedzminstvo.client.gui

import com.mojang.blaze3d.matrix.MatrixStack
import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.entity.capabilities.horseowner.IHorseOwner
import dev.sharpwave.wiedzminstvo.utils.HorseHelper
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.AbstractGui
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.inventory.InventoryScreen
import net.minecraft.client.util.InputMappings
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.entity.passive.horse.AbstractHorseEntity
import net.minecraft.entity.passive.horse.LlamaEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.DyeColor
import net.minecraft.util.RegistryKey
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.util.text.StringTextComponent
import net.minecraft.world.World
import net.minecraftforge.fml.common.ObfuscationReflectionHelper
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import kotlin.math.floor


class GuiStatViewer(player: PlayerEntity) : Screen(StringTextComponent("Horse Stat Viewer")) {
    private val xSize = 176
    private val ySize = 138
    private val owner: IHorseOwner
    private val horse: AbstractHorseEntity
    private val speed: Float
    private val jumpHeight: Float
    private val health: Float
    private val maxHealth: Float
    private val lastPos: Vector3d
    private val lastDim: RegistryKey<World>
    private val mc = Minecraft.getInstance()

    override fun render(stack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        this.renderBackground(stack)

        // GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bind(TEXTURE)

        val i: Int = (this.width - xSize) / 2
        val j: Int = (this.height - ySize) / 2
        AbstractGui.blit(stack, i, j, 0f, 0f, xSize, ySize, 256, 256)
        super.render(stack, mouseX, mouseY, partialTicks)
        InventoryScreen.renderEntityInInventory(
            i + 43, j + 68, 25, (i + 51).toFloat() - mouseX, (j + 75 - 50).toFloat() - mouseY,
            horse
        )
        AbstractGui.drawString(stack, mc.font, horse.name, i + 84, j + 10, DyeColor.WHITE.colorValue)
        AbstractGui.drawString(stack, mc.font, "Health:", i + 84, j + 30, DyeColor.LIGHT_GRAY.colorValue)
        AbstractGui.drawString(stack, mc.font, "$health/$maxHealth", i + 120, j + 30, DyeColor.WHITE.colorValue)
        AbstractGui.drawString(stack, mc.font, "Speed:", i + 84, j + 45, DyeColor.LIGHT_GRAY.colorValue)
        AbstractGui.drawString(
            stack,
            mc.font,
            speed.toString() + "",
            i + 120,
            j + 45,
            DyeColor.WHITE.colorValue
        )
        AbstractGui.drawString(stack, mc.font, "Jump Height:", i + 84, j + 60, DyeColor.LIGHT_GRAY.colorValue)
        AbstractGui.drawString(
            stack,
            mc.font,
            jumpHeight.toString() + "",
            i + 148,
            j + 60,
            DyeColor.WHITE.colorValue
        )
        AbstractGui.drawString(
            stack,
            mc.font,
            "Last known position:" + "",
            i + 8,
            j + 84,
            DyeColor.LIGHT_GRAY.colorValue
        )
        AbstractGui.drawString(
            stack,
            mc.font,
            if (lastPos == Vector3d.ZERO) "Unknown" else "xyz = " + lastPos.x.toString() + " " + lastPos.y
                .toString() + " " + lastPos.z,
            i + 8,
            j + 94,
            DyeColor.WHITE.colorValue
        )
        AbstractGui.drawString(
            stack,
            mc.font,
            "Last known dimension:" + "",
            i + 8,
            j + 110,
            DyeColor.LIGHT_GRAY.colorValue
        )
        AbstractGui.drawString(
            stack,
            mc.font,
            lastDim.location().toString(),
            i + 8,
            j + 120,
            DyeColor.WHITE.colorValue
        )
    }

    override fun isPauseScreen(): Boolean {
        return false
    }

    override fun shouldCloseOnEsc(): Boolean {
        return true
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (mc.options.keyInventory.isActiveAndMatches(InputMappings.getKey(keyCode, modifiers))) {
            mc.player?.clientSideCloseContainer()
            return true
        }
        return super.keyPressed(keyCode, scanCode, modifiers)
    }

    companion object {
        private val TEXTURE = ResourceLocation(WiedzminstvoMod.MODID, "textures/gui/horse_stat_viewer.png")
    }

    init {
        owner = HorseHelper.getOwnerCap(player)!!
        horse = owner.createHorseEntity(player.level)!!
        horse.attributes.load(owner.horseNBT.getList("Attributes", 10)) // Read

        // attributes
        horse.readAdditionalSaveData(owner.horseNBT)
        val cap = horse.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)

        cap.ifPresent { horseInventory: IItemHandler ->
            if (horseInventory.getStackInSlot(0)
                    .isEmpty && horse.isSaddleable
            ) horse.equipSaddle(null) // Set saddled
            if (horse is LlamaEntity) {
                try {
                    val setColor: Method = ObfuscationReflectionHelper.findMethod(
                        LlamaEntity::class.java, "func_190711_a",
                        DyeColor::class.java
                    )

                    val stack = horseInventory.getStackInSlot(1)
                    if (horse.isArmor(stack)) setColor.invoke(
                        horse,
                        DyeColor.byId(stack.damageValue)
                    ) else setColor.invoke(horse, null as DyeColor?)
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                }
            }
        }

        health = floor(horse.health.toDouble()).toFloat()
        maxHealth = (floor((horse.maxHealth * 10).toDouble()) / 10).toFloat()
        speed = (floor(horse.getAttribute(Attributes.MOVEMENT_SPEED)!!.value * 100) / 10).toFloat()
        jumpHeight = (floor(horse.getAttributeBaseValue(Attributes.JUMP_STRENGTH) * 100) / 10).toFloat()
        lastPos = owner.lastSeenPosition
        lastDim = owner.lastSeenDim
    }
}