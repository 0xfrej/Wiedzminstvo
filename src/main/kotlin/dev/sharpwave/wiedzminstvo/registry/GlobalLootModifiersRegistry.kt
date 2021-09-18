package dev.sharpwave.wiedzminstvo.registry

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.world.loot.KillDropModifier
import net.minecraftforge.common.loot.GlobalLootModifierSerializer
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.eventbus.KotlinEventBus
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object GlobalLootModifiersRegistry : IForgeRegistry {
    private val GLM: KDeferredRegister<GlobalLootModifierSerializer<*>> =
        KDeferredRegister(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, WiedzminstvoMod.MODID)

    override fun register(bus: KotlinEventBus) {
        GLM.register(bus)
    }

    val PIG_DROP by GLM.registerObject("pig_drop") { KillDropModifier.serializerFactory() }
}