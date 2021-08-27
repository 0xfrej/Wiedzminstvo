package dev.sharpwave.wiedzminstvo.inventory

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.IRecipeHolder
import net.minecraft.inventory.ItemStackHelper
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.util.NonNullList

class AlchemyResultInventory : IInventory, IRecipeHolder {
    private val itemStacks: NonNullList<ItemStack> = NonNullList.withSize(1, ItemStack.EMPTY)
    private var recipeUsed: IRecipe<*>? = null

    override fun getContainerSize(): Int {
        return 1
    }

    override fun isEmpty(): Boolean {
        val stackIterator: Iterator<*> = itemStacks.iterator()
        var testStack: ItemStack
        do {
            if (!stackIterator.hasNext()) {
                return true
            }
            testStack = stackIterator.next() as ItemStack
        } while (testStack.isEmpty)
        return false
    }

    override fun getItem(slotIndex: Int): ItemStack {
        return itemStacks[0]
    }

    override fun removeItem(slotX: Int, slotY: Int): ItemStack {
        return ItemStackHelper.takeItem(itemStacks, 0)
    }

    override fun removeItemNoUpdate(slotIndex: Int): ItemStack {
        return ItemStackHelper.takeItem(itemStacks, 0)
    }

    override fun setItem(slotIndex: Int, itemStack: ItemStack) {
        itemStacks[0] = itemStack
    }

    override fun setChanged() {}
    override fun stillValid(player: PlayerEntity): Boolean {
        return true
    }

    override fun clearContent() {
        itemStacks.clear()
    }

    override fun setRecipeUsed(recipe: IRecipe<*>?) {
        recipeUsed = recipe
    }

    override fun getRecipeUsed(): IRecipe<*>? {
        return recipeUsed
    }

}
