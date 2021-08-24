package dev.sharpwave.wiedzminstvo.world.loot

import net.minecraftforge.common.loot.GlobalLootModifierSerializer
import net.minecraftforge.common.loot.LootModifier

interface ISerializerFactory<T : LootModifier> {
    fun serializerFactory(): GlobalLootModifierSerializer<T>
}