package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.registries.GlobalLootModifiersRegistry as GLM
import dev.sharpwave.wiedzminstvo.tags.EntityTypeTags
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.getRegistryPath
import dev.sharpwave.wiedzminstvo.world.loot.HorseDropModifier
import net.minecraft.advancements.criterion.EntityPredicate
import net.minecraft.data.DataGenerator
import net.minecraft.loot.LootContext
import net.minecraft.loot.conditions.EntityHasProperty
import net.minecraftforge.common.data.GlobalLootModifierProvider
import net.minecraftforge.common.loot.GlobalLootModifierSerializer
import net.minecraftforge.common.loot.IGlobalLootModifier

class GlobalLootModifiers(generator: DataGenerator) : GlobalLootModifierProvider(generator, WiedzminstvoMod.MODID) {
    override fun start() {
        add(GLM.HORSE_DROP, HorseDropModifier(arrayOf(
            EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, predicate().of(EntityTypeTags.HORSES)).build()
        )))
    }

    private fun <T : IGlobalLootModifier> add(serializer: GlobalLootModifierSerializer<T>, instance: T) {
        add(getRegistryPath(serializer), serializer, instance)
    }

    private fun predicate(): EntityPredicate.Builder {
        return EntityPredicate.Builder.entity()
    }
}