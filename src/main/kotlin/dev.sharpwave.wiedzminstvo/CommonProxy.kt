package dev.sharpwave.wiedzminstvo

import dev.sharpwave.wiedzminstvo.capabilities.horseowner.HorseOwner
import dev.sharpwave.wiedzminstvo.capabilities.horseowner.HorseOwnerStorage
import dev.sharpwave.wiedzminstvo.capabilities.horseowner.IHorseOwner
import dev.sharpwave.wiedzminstvo.capabilities.storedhorse.HorseStorage
import dev.sharpwave.wiedzminstvo.capabilities.storedhorse.IStoredHorse
import dev.sharpwave.wiedzminstvo.capabilities.storedhorse.StoredHorse
import dev.sharpwave.wiedzminstvo.sound.WhistleSounds.registerSounds
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent


@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
class CommonProxy : IProxy {
    override val world: World?
        get() = null
    override val player: PlayerEntity?
        get() = null

    override fun displayStatViewer() {}

    companion object {
        fun setup(event: FMLCommonSetupEvent?) {


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