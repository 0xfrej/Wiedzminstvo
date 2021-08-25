package dev.sharpwave.wiedzminstvo.inventory.container

import dev.sharpwave.wiedzminstvo.inventory.AlchemyInventory
import dev.sharpwave.wiedzminstvo.inventory.AlchemyResultInventory
import dev.sharpwave.wiedzminstvo.registry.RecipeRegistry
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.IRecipeHolder
import net.minecraft.inventory.container.Slot
import net.minecraft.item.ItemStack
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.fml.hooks.BasicEventHooks

class AlchemyResultSlot(
    private val player: PlayerEntity,
    private val alchemySlots: AlchemyInventory,
    resultSlots: AlchemyResultInventory,
    index: Int,
    x: Int,
    y: Int
) :
    Slot(resultSlots, index, x, y) {
    private var removeCount = 0

    override fun mayPlace(p_75214_1_: ItemStack): Boolean {
        return false
    }

    override fun remove(index: Int): ItemStack {
        if (hasItem()) {
            removeCount += index.coerceAtMost(this.item.count)
        }
        return super.remove(index)
    }

    override fun onQuickCraft(items: ItemStack, removeCount: Int) {
        this.removeCount += removeCount
        checkTakeAchievements(items)
    }

    override fun onSwapCraft(removeCount: Int) {
        this.removeCount += removeCount
    }

    override fun checkTakeAchievements(items: ItemStack) {
        if (removeCount > 0) {
            items.onCraftedBy(player.level, player, removeCount)
            BasicEventHooks.firePlayerCraftingEvent(player, items, alchemySlots)
        }
        if (container is IRecipeHolder) {
            (container as IRecipeHolder).awardUsedRecipes(player)
        }
        removeCount = 0
    }

    override fun onTake(player: PlayerEntity, items: ItemStack): ItemStack {
        checkTakeAchievements(items)
        ForgeHooks.setCraftingPlayer(player)
        val remainingItems = player.level.recipeManager.getRemainingItemsFor(
            RecipeRegistry.ALCHEMY,
            alchemySlots, player.level
        )

        ForgeHooks.setCraftingPlayer(null)

        for (i in remainingItems.indices) {
            var item = alchemySlots.getItem(i)
            val remainingItem = remainingItems[i]
            if (!item.isEmpty) {
                alchemySlots.removeItem(i, 1)
                item = alchemySlots.getItem(i)
            }
            if (!remainingItem.isEmpty) {
                if (item.isEmpty) {
                    alchemySlots.setItem(i, remainingItem)
                } else if (ItemStack.isSame(item, remainingItem) && ItemStack.tagMatches(item, remainingItem)) {
                    remainingItem.grow(item.count)
                    alchemySlots.setItem(i, remainingItem)
                } else if (!this.player.inventory.add(remainingItem)) {
                    this.player.drop(remainingItem, false)
                }
            }
        }

        return items
    }
}