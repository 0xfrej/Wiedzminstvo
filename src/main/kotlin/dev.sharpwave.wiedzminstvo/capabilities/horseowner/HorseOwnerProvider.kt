package dev.sharpwave.wiedzminstvo.capabilities.horseowner

import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.common.util.LazyOptional


class HorseOwnerProvider : ICapabilitySerializable<CompoundNBT?> {
    private val instance = OWNER_CAPABILITY!!.defaultInstance
    override fun serializeNBT(): CompoundNBT {
        return OWNER_CAPABILITY!!.storage.writeNBT(OWNER_CAPABILITY, instance, null) as CompoundNBT
    }

    override fun deserializeNBT(nbt: CompoundNBT?) {
        OWNER_CAPABILITY!!.storage.readNBT(OWNER_CAPABILITY, instance, null, nbt)
    }

    override fun <T> getCapability(cap: Capability<T>, Dist: Direction?): LazyOptional<T> {
        return if (cap === OWNER_CAPABILITY) LazyOptional.of { instance!! } as LazyOptional<T> else LazyOptional.empty()
    }

    companion object {
        @CapabilityInject(IHorseOwner::class)
        val OWNER_CAPABILITY: Capability<IHorseOwner?>? = null
    }
}