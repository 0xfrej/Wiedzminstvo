package dev.sharpwave.wiedzminstvo.network.main.horse

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.network.main.horse.packets.HorseCapSyncPacket
import dev.sharpwave.wiedzminstvo.network.main.horse.packets.OwnerSyncShowStatsPacket
import dev.sharpwave.wiedzminstvo.network.main.horse.packets.PlayWhistlePacket
import dev.sharpwave.wiedzminstvo.network.main.horse.packets.PressKeyPacket
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkEvent
import java.util.*
import java.util.function.Supplier

@Suppress("INACCESSIBLE_TYPE")
object HorseSubNetwork {
    fun registerPackets() {
        WiedzminstvoMod.mainNetwork.registerMessage(0,
            HorseCapSyncPacket::class.java,
            { obj: HorseCapSyncPacket, buf: PacketBuffer -> obj.toBytes(buf) },
            { HorseCapSyncPacket() },
            { obj: HorseCapSyncPacket, ctx: Supplier<NetworkEvent.Context> -> obj.handle(ctx) },
            Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        )
        WiedzminstvoMod.mainNetwork.registerMessage(1,
            OwnerSyncShowStatsPacket::class.java,
            OwnerSyncShowStatsPacket::toBytes,
            { OwnerSyncShowStatsPacket() },
            OwnerSyncShowStatsPacket::handle,
            Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        )
        WiedzminstvoMod.mainNetwork.registerMessage(2,
            PressKeyPacket::class.java,
            PressKeyPacket::toBytes,
            { PressKeyPacket() },
            PressKeyPacket::handle,
            Optional.of(NetworkDirection.PLAY_TO_SERVER)
        )
        WiedzminstvoMod.mainNetwork.registerMessage(3,
            PlayWhistlePacket::class.java,
            PlayWhistlePacket::toBytes,
            { PlayWhistlePacket() },
            PlayWhistlePacket::handle,
            Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        )
    }
}