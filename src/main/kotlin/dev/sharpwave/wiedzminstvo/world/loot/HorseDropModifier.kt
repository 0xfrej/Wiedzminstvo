package dev.sharpwave.wiedzminstvo.world.loot

import com.google.gson.JsonObject
import dev.sharpwave.wiedzminstvo.utils.HorseHelper.getHorseCap
import net.minecraft.entity.Entity
import net.minecraft.entity.passive.horse.AbstractHorseEntity
import net.minecraft.item.ItemStack
import net.minecraft.loot.LootContext
import net.minecraft.loot.LootParameters
import net.minecraft.loot.conditions.ILootCondition
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.loot.GlobalLootModifierSerializer
import net.minecraftforge.common.loot.LootModifier

class HorseDropModifier constructor(conditionsIn: Array<ILootCondition>) : LootModifier(conditionsIn) {

    override fun doApply(generatedLoot: MutableList<ItemStack>, context: LootContext): List<ItemStack> {
        val entity: Entity? = context.getParamOrNull(LootParameters.THIS_ENTITY)
        if (entity is AbstractHorseEntity) {
            val horse = getHorseCap(entity)
            if (horse != null && horse.isOwned) {
                generatedLoot.clear()
            }
        }
        return generatedLoot
    }

    private class Serializer : GlobalLootModifierSerializer<HorseDropModifier>() {
        override fun read(
            location: ResourceLocation,
            jObject: JsonObject,
            ailootcondition: Array<ILootCondition>
        ): HorseDropModifier {
            return HorseDropModifier(ailootcondition)
        }

        override fun write(instance: HorseDropModifier): JsonObject {
            return makeConditions(instance.conditions)
        }
    }

    companion object : ISerializerFactory<HorseDropModifier> {
        override fun serializerFactory(): GlobalLootModifierSerializer<HorseDropModifier> {
            return Serializer()
        }
    }
}