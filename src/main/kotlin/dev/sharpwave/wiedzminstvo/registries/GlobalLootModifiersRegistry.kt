package dev.sharpwave.wiedzminstvo.registries

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.blocks.AlchemyFlowerBlock
import dev.sharpwave.wiedzminstvo.world.loot.HorseDropModifier
import net.minecraft.block.Block
import net.minecraft.potion.Effects
import net.minecraftforge.common.loot.GlobalLootModifierSerializer
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.eventbus.KotlinEventBus
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object GlobalLootModifiersRegistry : IForgeRegistry {
    private val GLM : KDeferredRegister<GlobalLootModifierSerializer<*>> = KDeferredRegister(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, WiedzminstvoMod.MODID)

    override fun register(bus: KotlinEventBus) {
        GLM.register(bus)
    }

    val HORSE_DROP by GLM.registerObject("horse_drop") { HorseDropModifier.serializerFactory() }
}