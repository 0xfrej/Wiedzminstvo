package dev.sharpwave.wiedzminstvo.entity.capabilities.storedhorse

import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.INBT
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.Capability.IStorage

class HorseStorage : IStorage<IStoredHorse> {
    override fun writeNBT(capability: Capability<IStoredHorse>?, instance: IStoredHorse?, side: Direction?): INBT? {
        if (instance != null) {
            val tag = CompoundNBT()
            tag.putString("owner", instance.ownerUUID)
            tag.putInt("horseNum", instance.horseNum)
            tag.putString("storage", instance.storageUUID)
            tag.putBoolean("owned", instance.isOwned)
            return tag
        }
        return null
    }

    override fun readNBT(
        capability: Capability<IStoredHorse>?,
        instance: IStoredHorse?,
        side: Direction?,
        nbt: INBT?
    ) {
        if (instance != null) {
            val tag = nbt as CompoundNBT
            instance.ownerUUID = tag.getString("owner")
            instance.horseNum = tag.getInt("horseNum")
            instance.storageUUID = tag.getString("storage")
            instance.isOwned = tag.getBoolean("owned")
        }
    }
}