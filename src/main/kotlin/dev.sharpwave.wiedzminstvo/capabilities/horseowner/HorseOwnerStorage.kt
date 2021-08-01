package dev.sharpwave.wiedzminstvo.capabilities.horseowner

import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.INBT
import net.minecraft.nbt.NBTUtil
import net.minecraft.util.Direction
import net.minecraft.util.RegistryKey
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.util.registry.Registry
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.Capability.IStorage


class HorseOwnerStorage : IStorage<IHorseOwner> {
    override fun writeNBT(capability: Capability<IHorseOwner>, instance: IHorseOwner, Dist: Direction): INBT {
        val tag = CompoundNBT()
        tag.put("horse", instance.horseNBT)
        tag.putInt("horseNum", instance.horseNum)
        tag.putString("uuid", instance.storageUUID)
        tag.put("lastSeenPos", NBTUtil.writeBlockPos(BlockPos(instance.lastSeenPosition)))
        tag.putString("lastSeenDim", instance.lastSeenDim.location().toString())
        return tag
    }

    override fun readNBT(capability: Capability<IHorseOwner>, instance: IHorseOwner, Dist: Direction, nbt: INBT) {
        val tag = nbt as CompoundNBT
        instance.horseNBT = tag.getCompound("horse")
        instance.horseNum = tag.getInt("horseNum")
        instance.storageUUID = tag.getString("uuid")
        val temp = NBTUtil.readBlockPos(tag.getCompound("lastSeenPos"))
        instance.lastSeenPosition = Vector3d(temp.x.toDouble(), temp.y.toDouble(), temp.z.toDouble())
        instance.lastSeenDim =
            RegistryKey.create(Registry.DIMENSION_REGISTRY, ResourceLocation(tag.getString("lastSeenDim")))
    }
}