package dev.sharpwave.wiedzminstvo.network

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.capabilities.horseowner.HorseOwnerProvider
import dev.sharpwave.wiedzminstvo.capabilities.horseowner.IHorseOwner
import dev.sharpwave.wiedzminstvo.utils.HorseHelper.getOwnerCap
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier


class OwnerSyncShowStatsPacket {
    private var ownerNBT: CompoundNBT? = null

    constructor() {}
    constructor(owner: IHorseOwner?) {
        ownerNBT = HorseOwnerProvider.OWNER_CAPABILITY!!.storage.writeNBT(
            HorseOwnerProvider.OWNER_CAPABILITY,
            owner,
            null
        ) as CompoundNBT?
    }

    constructor(buf: PacketBuffer) {
        ownerNBT = buf.readNbt()
    }

    fun toBytes(buf: PacketBuffer) {
        buf.writeNbt(ownerNBT)
    }

    fun handle(ctx: Supplier<NetworkEvent.Context>) {
        if (ctx.get().direction.receptionSide.isClient) {
            ctx.get().enqueueWork {
                val player: PlayerEntity? = WiedzminstvoMod.proxy.player
                if (player != null) {
                    val owner = getOwnerCap(player)
                    HorseOwnerProvider.OWNER_CAPABILITY!!.storage
                        .readNBT(HorseOwnerProvider.OWNER_CAPABILITY, owner, null, ownerNBT)
                    WiedzminstvoMod.proxy.displayStatViewer()
                }
            }
        }
    }
}