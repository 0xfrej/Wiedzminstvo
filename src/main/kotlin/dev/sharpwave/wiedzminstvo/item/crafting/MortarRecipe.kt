package dev.sharpwave.wiedzminstvo.item.crafting

import dev.sharpwave.wiedzminstvo.registry.ItemRegistry
import dev.sharpwave.wiedzminstvo.registry.RecipeRegistry
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.AbstractCookingRecipe
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.item.crafting.IRecipeType
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.ResourceLocation

class MortarRecipe(location: ResourceLocation, name: String, ingredient: Ingredient, item: ItemStack, experience: Float, grindingCount: Int) :
    AbstractCookingRecipe(IRecipeType.SMELTING, location, name, ingredient, item, experience, grindingCount) {
    override fun getToastSymbol(): ItemStack {
        return ItemStack(ItemRegistry.MORTAR)
    }

    override fun getSerializer(): IRecipeSerializer<*> {
        return RecipeRegistry.MORTAR_RECIPE
    }
}