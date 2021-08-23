package dev.sharpwave.wiedzminstvo.inventory.container

import dev.sharpwave.wiedzminstvo.inventory.AlchemyInventory
import dev.sharpwave.wiedzminstvo.registry.BlockRegistry
import dev.sharpwave.wiedzminstvo.registry.ContainerTypeRegistry
import dev.sharpwave.wiedzminstvo.tag.ItemTags
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.RecipeBookContainer
import net.minecraft.inventory.container.Slot
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.RecipeBookCategory
import net.minecraft.item.crafting.RecipeItemHelper
import net.minecraft.util.IWorldPosCallable
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import java.util.*

class AlchemyContainer(id: Int, private val inv: PlayerInventory, private val access: IWorldPosCallable) : RecipeBookContainer<AlchemyInventory>(ContainerTypeRegistry.ALCHEMY, id) {
   private val alchemySlots = AlchemyInventory(this, 3,1)
   private val random = Random()

   constructor(id: Int, inv: PlayerInventory) : this(id, inv, IWorldPosCallable.NULL)

   override fun removed(player: PlayerEntity) {
      super.removed(player)
      access.execute { level: World, pos: BlockPos ->
         clearContainer(
            player,
            player.level,
            alchemySlots
         )
      }
   }

   override fun stillValid(player: PlayerEntity): Boolean {
      return stillValid(access, player, BlockRegistry.ALCHEMY_TABLE)
   }

   override fun quickMoveStack(player: PlayerEntity, slotId: Int): ItemStack {
      var itemStack = ItemStack.EMPTY
      /*val slot = slots[slotId]
      if (slot != null && slot.hasItem()) {
         val itemStack1 = slot.item
         itemStack = itemStack1.copy()
         if (slotId == 0) {
            if (!moveItemStackTo(itemStack1, 2, 38, true)) {
               return ItemStack.EMPTY
            }
         } else if (slotId == 1) {
            if (!moveItemStackTo(itemStack1, 2, 38, true)) {
               return ItemStack.EMPTY
            }
         } else if (itemStack1.item === Items.LAPIS_LAZULI) {
            if (!moveItemStackTo(itemStack1, 1, 2, true)) {
               return ItemStack.EMPTY
            }
         } else {
            if (slots[0].hasItem() || !slots[0].mayPlace(itemStack1)) {
               return ItemStack.EMPTY
            }
            val itemstack2 = itemStack1.copy()
            itemstack2.count = 1
            itemStack1.shrink(1)
            slots[0].set(itemstack2)
         }
         if (itemStack1.isEmpty) {
            slot.set(ItemStack.EMPTY)
         } else {
            slot.setChanged()
         }
         if (itemStack1.count == itemStack.count) {
            return ItemStack.EMPTY
         }
         slot.onTake(player, itemStack1)
      }*/
      return itemStack
   }

   init {
      // middle
      addSlot(object : Slot(alchemySlots, 0, 113, 31) {
         override fun mayPlace(itemStack: ItemStack): Boolean {
            return ItemTags.ALCHEMY_FUSION_INGREDIENTS.contains(itemStack.item)
         }

         override fun getMaxStackSize(): Int {
            return 1
         }
      })
      // bottom
      addSlot(object : Slot(alchemySlots, 1, 113, 63) {
         override fun mayPlace(itemStack: ItemStack): Boolean {
            return ItemTags.ALCHEMY_INGREDIENTS.contains(itemStack.item)
         }

         override fun getMaxStackSize(): Int {
            return 1
         }
      })
      // top left
      addSlot(object : Slot(alchemySlots, 2, 83, 13) {
         override fun mayPlace(itemStack: ItemStack): Boolean {
            return ItemTags.ALCHEMY_INGREDIENTS.contains(itemStack.item)
         }

         override fun getMaxStackSize(): Int {
            return 1
         }
      })
      // top right
      addSlot(object : Slot(alchemySlots, 3, 142, 13) {
         override fun mayPlace(itemStack: ItemStack): Boolean {
            return ItemTags.ALCHEMY_INGREDIENTS.contains(itemStack.item)
         }

         override fun getMaxStackSize(): Int {
            return 1
         }
      })

      //player inv
      for (i in 0..2) {
         for (j in 0..8) {
            addSlot(Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18))
         }
      }
      for (k in 0..8) {
         addSlot(Slot(inv, k, 8 + k * 18, 142))
      }
   }

   override fun fillCraftSlotsStackedContents(handler: RecipeItemHelper) {
      alchemySlots.fillStackedContents(handler)
   }

   override fun clearCraftingContent() {
      alchemySlots.clearContent()
   }

   override fun recipeMatches(recipe: IRecipe<in AlchemyInventory>): Boolean {
      return recipe.matches(alchemySlots, inv.player.level)
   }

   override fun getResultSlotIndex(): Int {
      return 0
   }

   override fun getGridWidth(): Int {
      return alchemySlots.containerSize
   }

   override fun getGridHeight(): Int {
      return 1
   }

   @OnlyIn(Dist.CLIENT)
   override fun getSize(): Int {
      return alchemySlots.containerSize
   }

   @OnlyIn(Dist.CLIENT)
   override fun getRecipeBookType(): RecipeBookCategory {
      return RecipeBookCategory.CRAFTING
   }
}