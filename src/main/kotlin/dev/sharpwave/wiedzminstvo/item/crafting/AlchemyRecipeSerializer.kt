package dev.sharpwave.wiedzminstvo.item.crafting

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.item.crafting.Ingredient
import net.minecraft.item.crafting.ShapedRecipe
import net.minecraft.network.PacketBuffer
import net.minecraft.util.JSONUtils
import net.minecraft.util.NonNullList
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.ForgeRegistryEntry

class AlchemyRecipeSerializer(private val maxIngredients: Int = 4) : ForgeRegistryEntry<IRecipeSerializer<*>>(), IRecipeSerializer<AlchemyRecipe> {

    override fun fromJson(location: ResourceLocation, jObject: JsonObject): AlchemyRecipe {
        val group = JSONUtils.getAsString(jObject, "group", "")
        val ingredients = itemsFromJson(JSONUtils.getAsJsonArray(jObject, "ingredients"))
        return if (ingredients.isEmpty()) {
            throw JsonParseException("No ingredients for alchemy recipe")
        } else if (ingredients.size > maxIngredients) {
            throw JsonParseException("Too many ingredients for alchemy recipe the max is $maxIngredients")
        } else {
            val stack = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(jObject, "result"))
            AlchemyRecipe(location, group, stack, ingredients)
        }
    }

    override fun fromNetwork(location: ResourceLocation, buffer: PacketBuffer): AlchemyRecipe {
        val group = buffer.readUtf(32767)
        val i = buffer.readVarInt()
        val ingredients = NonNullList.withSize(i, Ingredient.EMPTY)
        for (j in ingredients.indices) {
            ingredients[j] = Ingredient.fromNetwork(buffer)
        }
        val stack = buffer.readItem()
        return AlchemyRecipe(location, group, stack, ingredients)
    }

    override fun toNetwork(buffer: PacketBuffer, recipe: AlchemyRecipe) {
        buffer.writeUtf(recipe.group)
        buffer.writeVarInt(recipe.ingredients.size)
        for (ingredient in recipe.ingredients) {
            ingredient.toNetwork(buffer)
        }
        buffer.writeItem(recipe.resultItem)
    }

    companion object {
        private fun itemsFromJson(json: JsonArray): NonNullList<Ingredient> {
            val ingredients = NonNullList.create<Ingredient>()
            for (i in 0 until json.size()) {
                val ingredient = Ingredient.fromJson(json[i])
                if (!ingredient.isEmpty) {
                    ingredients.add(ingredient)
                }
            }
            return ingredients
        }
    }
}