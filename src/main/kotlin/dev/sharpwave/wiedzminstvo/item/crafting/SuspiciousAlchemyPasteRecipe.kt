package dev.sharpwave.wiedzminstvo.item.crafting

import dev.sharpwave.wiedzminstvo.alchemy.IAlchemyIngredient
import dev.sharpwave.wiedzminstvo.alchemy.IngredientEffect
import dev.sharpwave.wiedzminstvo.inventory.AlchemyInventory
import dev.sharpwave.wiedzminstvo.item.SuspiciousAlchemyPaste
import dev.sharpwave.wiedzminstvo.registry.ItemRegistry
import dev.sharpwave.wiedzminstvo.registry.RecipeRegistry
import dev.sharpwave.wiedzminstvo.tag.ItemTags
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.item.crafting.IRecipeType
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

class SuspiciousAlchemyPasteRecipe(private val location: ResourceLocation) : IRecipe<AlchemyInventory> {

    override fun matches(inventory: AlchemyInventory, level: World): Boolean {
        var hasIngredient = false
        var hasPaste = false
        var hasOtherItems = false

        for (i in 0 until inventory.containerSize) {
            val stack = inventory.getItem(i)

            if (!stack.isEmpty) {
                if (stack.item === ItemRegistry.ALCHEMY_PASTE && !hasPaste) {
                    hasPaste = true
                } else if (ItemTags.ALCHEMY_INGREDIENTS.contains(stack.item) && !hasIngredient) {
                    hasIngredient = true
                } else if (hasPaste and hasPaste) {
                    hasOtherItems = true
                }
            }
        }

        return ! hasOtherItems and hasPaste and hasIngredient
    }

    override fun assemble(inventory: AlchemyInventory): ItemStack {
        var ingredientStack = ItemStack.EMPTY

        for (i in 0 until inventory.containerSize) {
            val testStack = inventory.getItem(i)
            if (!testStack.isEmpty && testStack.item.`is`(ItemTags.GROUND_ALCHEMY_INGREDIENTS)) {
                ingredientStack = testStack
                break
            }
        }
        val resultStack = ItemStack(ItemRegistry.SUSPICIOUS_ALCHEMY_PASTE, 1)
        if (ingredientStack.item is IAlchemyIngredient) {
            val item = (ingredientStack.item as IAlchemyIngredient)
            val effect = item.getEffect(IngredientEffect.Slot.THIRD)
            if (effect != null)
                SuspiciousAlchemyPaste.saveMobEffect(resultStack, effect.effect, effect.baseDuration)
        }
        return resultStack
    }

    override fun canCraftInDimensions(width: Int, height: Int): Boolean {
        return true
    }

    override fun getSerializer(): IRecipeSerializer<*> {
        return RecipeRegistry.SUSPICIOUS_PASTE_SERIALIZER
    }

    override fun getResultItem(): ItemStack {
        return ItemStack.EMPTY
    }

    override fun getId(): ResourceLocation {
        return location
    }

    override fun getType(): IRecipeType<*> {
        return RecipeRegistry.ALCHEMY
    }
}