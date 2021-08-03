package dev.sharpwave.wiedzminstvo.network.main.horse.packets

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.entity.capabilities.storedhorse.HorseProvider
import dev.sharpwave.wiedzminstvo.entity.capabilities.storedhorse.IStoredHorse
import dev.sharpwave.wiedzminstvo.network.AbstractNetworkPacket
import dev.sharpwave.wiedzminstvo.network.NetworkingUnit
import dev.sharpwave.wiedzminstvo.utils.HorseHelper.getHorseCap
import net.minecraft.entity.Entity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.PacketBuffer
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

@Suppress("unused")
class HorseCapSyncPacket() : AbstractNetworkPacket() {
    private var entityID = 0
    private var horseNBT: CompoundNBT? = null

    constructor(buf: PacketBuffer) : this() {
        entityID = buf.readInt()
        horseNBT = buf.readNbt()
    }

    constructor(entityID: Int, horse: IStoredHorse?) : this() {
        this.entityID = entityID
        horseNBT = HorseProvider.HORSE_CAPABILITY!!.storage.writeNBT(
            HorseProvider.HORSE_CAPABILITY,
            horse,
            null
        ) as CompoundNBT?
    }

    override fun toBytes(buf: PacketBuffer) {
        buf.writeInt(entityID)
        buf.writeNbt(horseNBT)
    }

    override fun handle(ctx: Supplier<NetworkEvent.Context>) {
        if (ctx.get().direction.receptionSide.isClient) {
            ctx.get().enqueueWork {
                val world: World = WiedzminstvoMod.proxy.world!!
                val e: Entity? = world.getEntity(entityID)
                if (e != null) {
                    val horse = getHorseCap(e)
                    HorseProvider.HORSE_CAPABILITY!!.storage
                        .readNBT(HorseProvider.HORSE_CAPABILITY, horse, null, horseNBT)
                }
            }
        }
    }

    companion object : NetworkingUnit()
}