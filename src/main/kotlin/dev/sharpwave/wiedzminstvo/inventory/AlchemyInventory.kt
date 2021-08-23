package dev.sharpwave.wiedzminstvo.inventory

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.IRecipeHelperPopulator
import net.minecraft.inventory.ItemStackHelper
import net.minecraft.inventory.container.Container
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.RecipeItemHelper
import net.minecraft.util.NonNullList

class AlchemyInventory(container: Container, ingredientSlots: Int, fuseSlots: Int) : IInventory, IRecipeHelperPopulator {
    private val items: NonNullList<ItemStack>
    private val menu: Container
    val ingredientSlots: Int
    val fuseSlots: Int

    init {
        items = NonNullList.withSize(ingredientSlots + fuseSlots, ItemStack.EMPTY)
        menu = container
        this.fuseSlots = fuseSlots
        this.ingredientSlots = ingredientSlots
    }

    override fun getContainerSize(): Int {
        return items.size
    }

    override fun isEmpty(): Boolean {
        val itemIterator = items.iterator()
        var testStack: ItemStack
        do {
            if (!itemIterator.hasNext()) {
                return true
            }
            testStack = itemIterator.next() as ItemStack
        } while (testStack.isEmpty)
        return false
    }

    override fun getItem(index: Int): ItemStack {
        return if (index >= this.containerSize) ItemStack.EMPTY else items[index]
    }

    override fun removeItemNoUpdate(index: Int): ItemStack {
        return ItemStackHelper.takeItem(items, index)
    }

    override fun removeItem(index: Int, param: Int): ItemStack {
        val newStack = ItemStackHelper.takeItem(items, index)
        if (!newStack.isEmpty) {
            menu.slotsChanged(this)
        }
        return newStack

    }

    override fun setItem(index: Int, stack: ItemStack) {
        items[index] = stack
        menu.slotsChanged(this)
    }

    override fun setChanged() {}

    override fun stillValid(player: PlayerEntity): Boolean {
        return true
    }

    override fun clearContent() {
        items.clear()
    }

    override fun fillStackedContents(recipeHelper: RecipeItemHelper) {
        val itemIterator = items.iterator()
        while (itemIterator.hasNext()) {
            val nextStack = itemIterator.next() as ItemStack
            recipeHelper.accountSimpleStack(nextStack)
        }
    }
}
