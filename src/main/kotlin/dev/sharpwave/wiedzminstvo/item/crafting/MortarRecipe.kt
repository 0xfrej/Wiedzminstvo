package dev.sharpwave.wiedzminstvo.item.crafting

import dev.sharpwave.wiedzminstvo.registry.ItemRegistry
import dev.sharpwave.wiedzminstvo.registry.RecipeRegistry
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.AbstractCookingRecipe
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.ResourceLocation

class MortarRecipe(
    location: ResourceLocation,
    group: String,
    ingredient: Ingredient,
    item: ItemStack,
    experience: Float,
    grindingCount: Int
) :
    AbstractCookingRecipe(RecipeRegistry.GRINDING, location, group, ingredient, item, experience, grindingCount) {

    override fun getToastSymbol(): ItemStack {
        return ItemStack(ItemRegistry.MORTAR)
    }

    override fun getSerializer(): IRecipeSerializer<*> {
        return RecipeRegistry.GRINDING_SERIALIZER
    }
}