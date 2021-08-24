package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.registry.ItemRegistry
import dev.sharpwave.wiedzminstvo.registry.GlobalLootModifiersRegistry as GLM
import dev.sharpwave.wiedzminstvo.tag.EntityTypeTags
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.getRegistryPath
import dev.sharpwave.wiedzminstvo.world.loot.HorseDropModifier
import dev.sharpwave.wiedzminstvo.world.loot.KillDropModifier
import net.minecraft.advancements.criterion.EntityPredicate
import net.minecraft.data.DataGenerator
import net.minecraft.entity.EntityType
import net.minecraft.loot.LootContext
import net.minecraft.loot.conditions.EntityHasProperty
import net.minecraft.loot.conditions.RandomChanceWithLooting
import net.minecraftforge.common.data.GlobalLootModifierProvider
import net.minecraftforge.common.loot.GlobalLootModifierSerializer
import net.minecraftforge.common.loot.IGlobalLootModifier

class GlobalLootModifiers(generator: DataGenerator) : GlobalLootModifierProvider(generator, WiedzminstvoMod.MODID) {
    override fun start() {
        add(GLM.HORSE_DROP, HorseDropModifier(arrayOf(
            EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, predicate().of(EntityTypeTags.HORSES)).build()
        )))
        add(GLM.PIG_DROP, KillDropModifier(arrayOf(
            EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, predicate().of(EntityTypeTags.PIGLIKE)).build(),
            RandomChanceWithLooting.randomChanceAndLootingBoost(0.6F, 0.1F).build()
        ), ItemRegistry.PORK_FAT, Pair(1, 3)))
    }

    private fun <T : IGlobalLootModifier> add(serializer: GlobalLootModifierSerializer<T>, instance: T) {
        add(getRegistryPath(serializer), serializer, instance)
    }

    private fun predicate(): EntityPredicate.Builder {
        return EntityPredicate.Builder.entity()
    }
}