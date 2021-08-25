package dev.sharpwave.wiedzminstvo.inventory.container

import dev.sharpwave.wiedzminstvo.inventory.AlchemyInventory
import dev.sharpwave.wiedzminstvo.inventory.AlchemyResultInventory
import dev.sharpwave.wiedzminstvo.registry.BlockRegistry
import dev.sharpwave.wiedzminstvo.registry.ContainerTypeRegistry
import dev.sharpwave.wiedzminstvo.registry.RecipeRegistry
import dev.sharpwave.wiedzminstvo.tag.ItemTags
import dev.sharpwave.wiedzminstvo.utils.RecipeHelper
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.RecipeBookContainer
import net.minecraft.inventory.container.Slot
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.RecipeBookCategory
import net.minecraft.item.crafting.RecipeItemHelper
import net.minecraft.network.play.server.SSetSlotPacket
import net.minecraft.util.IWorldPosCallable
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

// TODO: FIX crashing from i think quickStack move
class AlchemyContainer(id: Int, private val inv: PlayerInventory, private val access: IWorldPosCallable) :
    Container(ContainerTypeRegistry.ALCHEMY, id) {
    private val alchemySlots = AlchemyInventory(this, 3, 1)
    private val resultSlots = AlchemyResultInventory()

    constructor(id: Int, inv: PlayerInventory) : this(id, inv, IWorldPosCallable.NULL)

    override fun removed(player: PlayerEntity) {
        super.removed(player)
        access.execute { level: World, pos: BlockPos ->
            clearContainer(
                player,
                level,
                alchemySlots
            )
        }
    }

    override fun stillValid(player: PlayerEntity): Boolean {
        return stillValid(access, player, BlockRegistry.ALCHEMY_TABLE)
    }

    override fun slotsChanged(inventory: IInventory) {
        access.execute { level, pos ->
            run {
                if (!level.isClientSide) {
                    val player = inv.player as ServerPlayerEntity
                    var newStack = ItemStack.EMPTY
                    val recipeOptional = RecipeHelper.getRecipeOptionalFor(RecipeRegistry.ALCHEMY, alchemySlots, level)

                    if (recipeOptional.isPresent) {
                        val recipe = recipeOptional.get()
                        if (resultSlots.setRecipeUsed(level, player, recipe)) {
                            newStack = recipe.assemble(alchemySlots)
                        }
                    }

                    resultSlots.setItem(0, newStack)
                    player.connection.send(SSetSlotPacket(containerId, 0, newStack))
                }
            }
        }
    }

//    override fun quickMoveStack(player: PlayerEntity, slotId: Int): ItemStack {
//        var itemStack = ItemStack.EMPTY
//        /*val slot = slots[slotId]
//        if (slot != null && slot.hasItem()) {
//           val itemStack1 = slot.item
//           itemStack = itemStack1.copy()
//           if (slotId == 0) {
//              if (!moveItemStackTo(itemStack1, 2, 38, true)) {
//                 return ItemStack.EMPTY
//              }
//           } else if (slotId == 1) {
//              if (!moveItemStackTo(itemStack1, 2, 38, true)) {
//                 return ItemStack.EMPTY
//              }
//           } else if (itemStack1.item === Items.LAPIS_LAZULI) {
//              if (!moveItemStackTo(itemStack1, 1, 2, true)) {
//                 return ItemStack.EMPTY
//              }
//           } else {
//              if (slots[0].hasItem() || !slots[0].mayPlace(itemStack1)) {
//                 return ItemStack.EMPTY
//              }
//              val itemstack2 = itemStack1.copy()
//              itemstack2.count = 1
//              itemStack1.shrink(1)
//              slots[0].set(itemstack2)
//           }
//           if (itemStack1.isEmpty) {
//              slot.set(ItemStack.EMPTY)
//           } else {
//              slot.setChanged()
//           }
//           if (itemStack1.count == itemStack.count) {
//              return ItemStack.EMPTY
//           }
//           slot.onTake(player, itemStack1)
//        }*/
//        return itemStack
//    }

    init {
        addSlot(AlchemyResultSlot(inv.player, alchemySlots, resultSlots, 0, 151, 63))
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
            override fun getMaxStackSize(): Int {
                return 1
            }
        })
        // top left
        addSlot(object : Slot(alchemySlots, 2, 83, 13) {
            override fun getMaxStackSize(): Int {
                return 1
            }
        })
        // top right
        addSlot(object : Slot(alchemySlots, 3, 142, 13) {
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
}