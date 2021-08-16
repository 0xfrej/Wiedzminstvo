package dev.sharpwave.wiedzminstvo.tileentity

import net.minecraft.block.BlockState
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.EnchantingTableTileEntity
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.INameable
import net.minecraft.util.math.MathHelper
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import java.util.*

class AlchemyTableTileEntity : TileEntity(TileEntityType.ENCHANTING_TABLE), INameable,
    ITickableTileEntity {
    var time = 0
    var flip = 0f
    var oFlip = 0f
    var flipT = 0f
    var flipA = 0f
    var open = 0f
    var oOpen = 0f
    var rot = 0f
    var oRot = 0f
    var tRot = 0f
    private var name: ITextComponent? = null
    override fun save(p_189515_1_: CompoundNBT): CompoundNBT {
        super.save(p_189515_1_)
        if (hasCustomName()) {
            p_189515_1_.putString("CustomName", ITextComponent.Serializer.toJson(name))
        }
        return p_189515_1_
    }

    override fun load(p_230337_1_: BlockState, p_230337_2_: CompoundNBT) {
        super.load(p_230337_1_, p_230337_2_)
        if (p_230337_2_.contains("CustomName", 8)) {
            name = ITextComponent.Serializer.fromJson(p_230337_2_.getString("CustomName"))
        }
    }

    override fun tick() {
        oOpen = open
        oRot = rot
        val playerentity = level!!.getNearestPlayer(
            worldPosition.x.toDouble() + 0.5,
            worldPosition.y.toDouble() + 0.5,
            worldPosition.z.toDouble() + 0.5,
            3.0,
            false
        )
        if (playerentity != null) {
            val d0 = playerentity.x - (worldPosition.x.toDouble() + 0.5)
            val d1 = playerentity.z - (worldPosition.z.toDouble() + 0.5)
            tRot = MathHelper.atan2(d1, d0).toFloat()
            open += 0.1f
            if (open < 0.5f || EnchantingTableTileEntity.Companion.RANDOM.nextInt(40) == 0) {
                val f1 = flipT
                do {
                    flipT += (EnchantingTableTileEntity.Companion.RANDOM.nextInt(4) - EnchantingTableTileEntity.Companion.RANDOM.nextInt(
                        4
                    )).toFloat()
                } while (f1 == flipT)
            }
        } else {
            tRot += 0.02f
            open -= 0.1f
        }
        while (rot >= Math.PI.toFloat()) {
            rot -= Math.PI.toFloat() * 2f
        }
        while (rot < -Math.PI.toFloat()) {
            rot += Math.PI.toFloat() * 2f
        }
        while (tRot >= Math.PI.toFloat()) {
            tRot -= Math.PI.toFloat() * 2f
        }
        while (tRot < -Math.PI.toFloat()) {
            tRot += Math.PI.toFloat() * 2f
        }
        var f2: Float
        f2 = tRot - rot
        while (f2 >= Math.PI.toFloat()) {
            f2 -= Math.PI.toFloat() * 2f
        }
        while (f2 < -Math.PI.toFloat()) {
            f2 += Math.PI.toFloat() * 2f
        }
        rot += f2 * 0.4f
        open = MathHelper.clamp(open, 0.0f, 1.0f)
        ++time
        oFlip = flip
        var f = (flipT - flip) * 0.4f
        val f3 = 0.2f
        f = MathHelper.clamp(f, -0.2f, 0.2f)
        flipA += (f - flipA) * 0.9f
        flip += flipA
    }

    override fun getName(): ITextComponent {
        return (if (name != null) name else TranslationTextComponent("container.enchant")) as ITextComponent
    }

    fun setCustomName(p_200229_1_: ITextComponent?) {
        name = p_200229_1_
    }

    override fun getCustomName(): ITextComponent? {
        return name
    }

    companion object {
        private val RANDOM = Random()
    }
}