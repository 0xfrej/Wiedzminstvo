package dev.sharpwave.wiedzminstvo

import dev.sharpwave.wiedzminstvo.capabilities.horseowner.HorseOwner
import dev.sharpwave.wiedzminstvo.capabilities.horseowner.HorseOwnerStorage
import dev.sharpwave.wiedzminstvo.capabilities.horseowner.IHorseOwner
import dev.sharpwave.wiedzminstvo.capabilities.storedhorse.HorseStorage
import dev.sharpwave.wiedzminstvo.capabilities.storedhorse.IStoredHorse
import dev.sharpwave.wiedzminstvo.capabilities.storedhorse.StoredHorse
import dev.sharpwave.wiedzminstvo.network.HorseCapSyncPacket
import dev.sharpwave.wiedzminstvo.network.OwnerSyncShowStatsPacket
import dev.sharpwave.wiedzminstvo.network.PlayWhistlePacket
import dev.sharpwave.wiedzminstvo.network.PressKeyPacket
import dev.sharpwave.wiedzminstvo.sound.WhistleSounds.registerSounds
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.network.PacketBuffer
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkEvent
import net.minecraftforge.fml.network.NetworkRegistry
import java.util.*
import java.util.function.Supplier


@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
class CommonProxy : IProxy {
    override val world: World?
        get() = null
    override val player: PlayerEntity?
        get() = null

    override fun displayStatViewer() {}

    companion object {
        fun setup(event: FMLCommonSetupEvent?) {
            val version: String = WiedzminstvoMod.info.version.toString()
            WiedzminstvoMod.network =
                NetworkRegistry.newSimpleChannel(ResourceLocation(WiedzminstvoMod.ID, "callablehorseschannel"),
                    { version },
                    { anObject: String? -> version.equals(anObject) }
                ) { anObject: String? -> version.equals(anObject) }

            @Suppress("INACCESSIBLE_TYPE")
            WiedzminstvoMod.network.registerMessage(0,
                HorseCapSyncPacket::class.java,
                { obj: HorseCapSyncPacket, buf: PacketBuffer -> obj.toBytes(buf) },
                { HorseCapSyncPacket() },
                { obj: HorseCapSyncPacket, ctx: Supplier<NetworkEvent.Context> -> obj.handle(ctx) },
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            )
            @Suppress("INACCESSIBLE_TYPE")
            WiedzminstvoMod.network.registerMessage(1,
                OwnerSyncShowStatsPacket::class.java,
                OwnerSyncShowStatsPacket::toBytes,
                { OwnerSyncShowStatsPacket() },
                OwnerSyncShowStatsPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            )
            @Suppress("INACCESSIBLE_TYPE")
            WiedzminstvoMod.network.registerMessage(2,
                PressKeyPacket::class.java,
                PressKeyPacket::toBytes,
                { PressKeyPacket() },
                PressKeyPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
            )
            @Suppress("INACCESSIBLE_TYPE")
            WiedzminstvoMod.network.registerMessage(3,
                PlayWhistlePacket::class.java,
                PlayWhistlePacket::toBytes,
                { PlayWhistlePacket() },
                PlayWhistlePacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            )

            registerSounds()

            // Caps
            CapabilityManager.INSTANCE.register(
                IHorseOwner::class.java, HorseOwnerStorage()
            ) { HorseOwner() }
            CapabilityManager.INSTANCE.register(
                IStoredHorse::class.java, HorseStorage()
            ) { StoredHorse() }
        }
    }
}