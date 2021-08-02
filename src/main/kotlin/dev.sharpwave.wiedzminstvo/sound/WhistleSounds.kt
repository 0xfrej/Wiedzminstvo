package dev.sharpwave.wiedzminstvo.sound

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus


@EventBusSubscriber(bus = Bus.FORGE, modid = WiedzminstvoMod.MODID)
object WhistleSounds {
	var randomWhistle: SoundEvent? = null
	private val sounds = ArrayList<SoundEvent>()

	fun registerSounds() {
		randomWhistle = registerSound("whistle")
	}

	private fun registerSound(soundName: String): SoundEvent {
		val soundID = ResourceLocation(WiedzminstvoMod.MODID, soundName)
		val s = SoundEvent(soundID)
		s.setRegistryName(soundID)
		sounds.add(s)
		return s
	}

	@SubscribeEvent
	fun onRegistry(event: RegistryEvent.Register<SoundEvent?>) {
		event.registry.registerAll(*sounds.toTypedArray())
	}
}