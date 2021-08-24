package dev.sharpwave.wiedzminstvo.item.crafting

import dev.sharpwave.wiedzminstvo.inventory.AlchemyInventory
import dev.sharpwave.wiedzminstvo.registry.ItemRegistry
import dev.sharpwave.wiedzminstvo.registry.RecipeRegistry
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.item.crafting.IRecipeType
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.NonNullList
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.common.util.RecipeMatcher

class AlchemyRecipe(private val id: ResourceLocation, private val group: String, private val result: ItemStack, private val ingredientList: NonNullList<Ingredient>) : IRecipe<AlchemyInventory> {

    override fun matches(inventory: AlchemyInventory, level: World): Boolean {
        val inputs: MutableList<ItemStack> = emptyList<ItemStack>().toMutableList()

        var i = 0

        for (j in 0 until inventory.containerSize) {
            val stack: ItemStack = inventory.getItem(j)
            if (!stack.isEmpty) {
                ++i
                inputs.add(stack)
            }
        }

        return i == this.ingredients.size && RecipeMatcher.findMatches(inputs, this.ingredients) != null
    }

    override fun assemble(inventory: AlchemyInventory): ItemStack {
        return result.copy()
    }

    override fun canCraftInDimensions(p_194133_1_: Int, p_194133_2_: Int): Boolean {
        return true
    }

    override fun getSerializer(): IRecipeSerializer<*> {
        return IRecipeSerializer.SUSPICIOUS_STEW
    }

    override fun getResultItem(): ItemStack {
        return result
    }

    override fun getId(): ResourceLocation {
        return id
    }

    override fun getType(): IRecipeType<*> {
        return RecipeRegistry.ALCHEMY
    }

    override fun getIngredients(): NonNullList<Ingredient> {
        return ingredientList
    }

    override fun getGroup(): String {
        return group
    }

    override fun getToastSymbol(): ItemStack {
        return ItemStack(ItemRegistry.ALCHEMY_TABLE)
    }
}