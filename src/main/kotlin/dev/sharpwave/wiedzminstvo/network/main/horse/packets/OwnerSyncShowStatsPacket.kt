package dev.sharpwave.wiedzminstvo.network.main.horse.packets

import dev.sharpwave.wiedzminstvo.client.gui.GuiStatViewer
import dev.sharpwave.wiedzminstvo.entity.capabilities.horseowner.HorseOwnerProvider
import dev.sharpwave.wiedzminstvo.entity.capabilities.horseowner.IHorseOwner
import dev.sharpwave.wiedzminstvo.network.AbstractNetworkPacket
import dev.sharpwave.wiedzminstvo.network.NetworkingUnit
import dev.sharpwave.wiedzminstvo.utils.HorseHelper.getOwnerCap
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

@Suppress("unused")
class OwnerSyncShowStatsPacket() : AbstractNetworkPacket() {
    private var ownerNBT: CompoundNBT? = null

    constructor(owner: IHorseOwner?) : this() {
        ownerNBT = HorseOwnerProvider.OWNER_CAPABILITY!!.storage.writeNBT(
            HorseOwnerProvider.OWNER_CAPABILITY,
            owner,
            null
        ) as CompoundNBT?
    }

    constructor(buf: PacketBuffer) : this() {
        ownerNBT = buf.readNbt()
    }

    override fun toBytes(buf: PacketBuffer) {
        buf.writeNbt(ownerNBT)
    }

    override fun handle(ctx: Supplier<NetworkEvent.Context>) {
        if (ctx.get().direction.receptionSide.isClient) {
            ctx.get().enqueueWork {
                val player: PlayerEntity? = Minecraft.getInstance().player
                if (player != null) {
                    val owner = getOwnerCap(player)
                    HorseOwnerProvider.OWNER_CAPABILITY!!.storage
                        .readNBT(HorseOwnerProvider.OWNER_CAPABILITY, owner, null, ownerNBT)
                    Minecraft.getInstance().setScreen(GuiStatViewer(player))
                }
            }
        }
        ctx.get().packetHandled = true
    }

    companion object : NetworkingUnit()
}