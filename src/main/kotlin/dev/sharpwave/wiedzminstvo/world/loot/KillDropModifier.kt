package dev.sharpwave.wiedzminstvo.world.loot

import com.google.gson.JsonObject
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.loot.LootContext
import net.minecraft.loot.conditions.ILootCondition
import net.minecraft.util.JSONUtils
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.MathHelper
import net.minecraftforge.common.loot.GlobalLootModifierSerializer
import net.minecraftforge.common.loot.LootModifier
import net.minecraftforge.registries.ForgeRegistries
import java.util.Random

class KillDropModifier constructor(conditionsIn: Array<ILootCondition>, private val reward: Item, private val range: Pair<Int, Int>) : LootModifier(conditionsIn) {

    override fun doApply(generatedLoot: MutableList<ItemStack>, context: LootContext): List<ItemStack> {
        generatedLoot.add(ItemStack(reward, MathHelper.nextInt(RANDOM, range.first, range.second)))
        return generatedLoot
    }

    private class Serializer : GlobalLootModifierSerializer<KillDropModifier>() {
        override fun read(location: ResourceLocation, jObject: JsonObject, ailootcondition: Array<ILootCondition>): KillDropModifier {
            val rangeCompound = JSONUtils.getAsJsonObject(jObject, "range")
            return KillDropModifier(
                ailootcondition,
                ForgeRegistries.ITEMS.getValue(ResourceLocation(JSONUtils.getAsString(jObject, "reward")))!!,
                Pair(rangeCompound.get("min").asInt, rangeCompound.get("max").asInt)
            )
        }

        override fun write(instance: KillDropModifier): JsonObject {
            val jObject = makeConditions(instance.conditions)
            val rangeObject = JsonObject()
            rangeObject.addProperty("min", instance.range.first)
            rangeObject.addProperty("max", instance.range.second)
            jObject.addProperty("reward", ForgeRegistries.ITEMS.getKey(instance.reward)!!.toString())
            jObject.add("range", rangeObject)
            return jObject
        }
    }

    companion object : ISerializerFactory<KillDropModifier> {
        override fun serializerFactory(): GlobalLootModifierSerializer<KillDropModifier> {
            return Serializer()
        }

        private val RANDOM = Random()
    }
}