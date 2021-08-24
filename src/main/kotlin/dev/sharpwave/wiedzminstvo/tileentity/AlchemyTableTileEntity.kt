package dev.sharpwave.wiedzminstvo.tileentity

import dev.sharpwave.wiedzminstvo.locale.AlchemyStrings
import dev.sharpwave.wiedzminstvo.registry.TileEntityRegistry
import net.minecraft.block.BlockState
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.INameable
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent

class AlchemyTableTileEntity : TileEntity(TileEntityRegistry.ALCHEMY_TABLE), INameable {

    private var name: ITextComponent? = null
    override fun save(tag: CompoundNBT): CompoundNBT {
        super.save(tag)
        if (hasCustomName()) {
            tag.putString("CustomName", ITextComponent.Serializer.toJson(name!!))
        }
        return tag
    }

    override fun load(state: BlockState, tag: CompoundNBT) {
        super.load(state, tag)
        if (tag.contains("CustomName", 8)) {
            name = ITextComponent.Serializer.fromJson(tag.getString("CustomName"))
        }
    }

    override fun getName(): ITextComponent {
        return (if (name != null) name else TranslationTextComponent(AlchemyStrings.ALCHEMY_TABLE_CONTAINER)) as ITextComponent
    }

    fun setCustomName(text: ITextComponent) {
        name = text
    }

    override fun getCustomName(): ITextComponent? {
        return name
    }
}