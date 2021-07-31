package dev.sharpwave.wiedzminstvo.capabilities.storedhorse

import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.common.util.LazyOptional


class HorseProvider : ICapabilitySerializable<CompoundNBT?> {
    private val instance = HORSE_CAPABILITY!!.defaultInstance

    override fun serializeNBT(): CompoundNBT {
        return HORSE_CAPABILITY!!.storage.writeNBT(HORSE_CAPABILITY, instance, null) as CompoundNBT
    }

    override fun deserializeNBT(nbt: CompoundNBT?) {
        HORSE_CAPABILITY!!.storage.readNBT(HORSE_CAPABILITY, instance, null, nbt)
    }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        return if (cap === HORSE_CAPABILITY) LazyOptional.of { instance!! } as LazyOptional<T> else LazyOptional.empty()
    }

    companion object {
        @CapabilityInject(IStoredHorse::class)
        val HORSE_CAPABILITY: Capability<IStoredHorse?>? = null
    }
}