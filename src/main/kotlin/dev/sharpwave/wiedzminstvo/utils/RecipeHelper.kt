package dev.sharpwave.wiedzminstvo.utils

import net.minecraft.inventory.IInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.IRecipeType
import net.minecraft.world.World
import java.util.*

object RecipeHelper {
    fun <C:IInventory, T:IRecipe<C>> canCraftFromItem(recipeType: IRecipeType<T>, inventory: C, level: World): Boolean {
        return level.recipeManager.getRecipeFor(recipeType, inventory, level).isPresent
    }

    fun <T:IRecipe<IInventory>> canCraftFromItem(recipeType: IRecipeType<T>, inputStack: ItemStack, level: World): Boolean {
        return level.recipeManager.getRecipeFor(recipeType, Inventory(inputStack) as IInventory, level).isPresent
    }

    fun <T:IRecipe<IInventory>> getRecipeFor(recipeType: IRecipeType<T>, inputStack: ItemStack, level: World): IRecipe<IInventory>? {
        val r = level.recipeManager.getRecipeFor(recipeType, Inventory(inputStack) as IInventory, level)
        return if (r.isPresent)
            r.get()
        else null
    }

    fun <T:IRecipe<IInventory>> getRecipeOptionalFor(recipeType: IRecipeType<T>, inputStack: ItemStack, level: World): Optional<T> {
        return level.recipeManager.getRecipeFor(recipeType, Inventory(inputStack) as IInventory, level)
    }

    fun <T:IRecipe<IInventory>> getRecipeProductFor(recipeType: IRecipeType<T>,inputStack: ItemStack, level: World): ItemStack {
        return getRecipeFor(recipeType, inputStack, level)?.resultItem ?: ItemStack.EMPTY
    }
}