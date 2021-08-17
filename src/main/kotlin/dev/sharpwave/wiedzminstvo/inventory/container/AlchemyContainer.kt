package dev.sharpwave.wiedzminstvo.inventory.container

import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.block.Blocks
import net.minecraft.enchantment.EnchantmentData
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.ContainerType
import net.minecraft.inventory.container.Slot
import net.minecraft.item.EnchantedBookItem
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.stats.Stats
import net.minecraft.util.IWorldPosCallable
import net.minecraft.util.IntReferenceHolder
import net.minecraft.util.SoundCategory
import net.minecraft.util.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.common.Tags
import net.minecraftforge.event.ForgeEventFactory
import java.util.*

class AlchemyContainer(p_i50086_1_: Int, p_i50086_2_: PlayerInventory, private val access: IWorldPosCallable) : Container(ContainerType.ENCHANTMENT, p_i50086_1_) {
   private val enchantSlots: IInventory = object : Inventory(2) {
      override fun setChanged() {
         super.setChanged()
         slotsChanged(this)
      }
   }
   private val random = Random()
   private val enchantmentSeed = IntReferenceHolder.standalone()
   val costs = IntArray(3)
   val enchantClue = intArrayOf(-1, -1, -1)
   val levelClue = intArrayOf(-1, -1, -1)

   constructor(p_i50085_1_: Int, p_i50085_2_: PlayerInventory) : this(
      p_i50085_1_,
      p_i50085_2_,
      IWorldPosCallable.NULL
   ) {
   }

   private fun getPower(world: World, pos: BlockPos): Float {
      return world.getBlockState(pos).getEnchantPowerBonus(world, pos)
   }

   override fun slotsChanged(p_75130_1_: IInventory) {
      if (p_75130_1_ === enchantSlots) {
         val itemstack = p_75130_1_.getItem(0)
         if (!itemstack.isEmpty && itemstack.isEnchantable) {
            access.execute { p_217002_2_: World, p_217002_3_: BlockPos ->
               var power = 0
               for (k in -1..1) {
                  for (l in -1..1) {
                     if ((k != 0 || l != 0) && p_217002_2_.isEmptyBlock(
                           p_217002_3_.offset(
                              l,
                              0,
                              k
                           )
                        ) && p_217002_2_.isEmptyBlock(p_217002_3_.offset(l, 1, k))
                     ) {
                        power += getPower(p_217002_2_, p_217002_3_.offset(l * 2, 0, k * 2)).toInt()
                        power += getPower(p_217002_2_, p_217002_3_.offset(l * 2, 1, k * 2)).toInt()
                        if (l != 0 && k != 0) {
                           power += getPower(p_217002_2_, p_217002_3_.offset(l * 2, 0, k)).toInt()
                           power += getPower(p_217002_2_, p_217002_3_.offset(l * 2, 1, k)).toInt()
                           power += getPower(p_217002_2_, p_217002_3_.offset(l, 0, k * 2)).toInt()
                           power += getPower(p_217002_2_, p_217002_3_.offset(l, 1, k * 2)).toInt()
                        }
                     }
                  }
               }
               random.setSeed(enchantmentSeed.get().toLong())
               for (i1 in 0..2) {
                  costs[i1] =
                     EnchantmentHelper.getEnchantmentCost(random, i1, power, itemstack)
                  enchantClue[i1] = -1
                  levelClue[i1] = -1
                  if (costs[i1] < i1 + 1) {
                     costs[i1] = 0
                  }
                  costs[i1] = ForgeEventFactory.onEnchantmentLevelSet(
                     p_217002_2_,
                     p_217002_3_,
                     i1,
                     power,
                     itemstack,
                     costs[i1]
                  )
               }
               for (j1 in 0..2) {
                  if (costs[j1] > 0) {
                     val list =
                        getEnchantmentList(itemstack, j1, costs[j1])
                     if (list != null && !list.isEmpty()) {
                        val enchantmentdata = list[random.nextInt(list.size)]
                        enchantClue[j1] =
                           Registry.ENCHANTMENT.getId(enchantmentdata.enchantment)
                        levelClue[j1] = enchantmentdata.level
                     }
                  }
               }
               broadcastChanges()
            }
         } else {
            for (i in 0..2) {
               costs[i] = 0
               enchantClue[i] = -1
               levelClue[i] = -1
            }
         }
      }
   }

   override fun clickMenuButton(p_75140_1_: PlayerEntity, p_75140_2_: Int): Boolean {
      val itemstack = enchantSlots.getItem(0)
      val itemstack1 = enchantSlots.getItem(1)
      val i = p_75140_2_ + 1
      return if ((itemstack1.isEmpty || itemstack1.count < i) && !p_75140_1_.abilities.instabuild) {
         false
      } else if (costs[p_75140_2_] <= 0 || itemstack.isEmpty || (p_75140_1_.experienceLevel < i || p_75140_1_.experienceLevel < costs[p_75140_2_]) && !p_75140_1_.abilities.instabuild) {
         false
      } else {
         access.execute { p_217003_6_: World, p_217003_7_: BlockPos? ->
            var itemstack2 = itemstack
            val list =
               getEnchantmentList(itemstack, p_75140_2_, costs[p_75140_2_])
            if (!list.isEmpty()) {
               p_75140_1_.onEnchantmentPerformed(itemstack, i)
               val flag = itemstack.item === Items.BOOK
               if (flag) {
                  itemstack2 = ItemStack(Items.ENCHANTED_BOOK)
                  val compoundnbt = itemstack.tag
                  if (compoundnbt != null) {
                     itemstack2.tag = compoundnbt.copy()
                  }
                  enchantSlots.setItem(0, itemstack2)
               }
               for (j in list.indices) {
                  val enchantmentdata = list[j]
                  if (flag) {
                     EnchantedBookItem.addEnchantment(itemstack2, enchantmentdata)
                  } else {
                     itemstack2.enchant(enchantmentdata.enchantment, enchantmentdata.level)
                  }
               }
               if (!p_75140_1_.abilities.instabuild) {
                  itemstack1.shrink(i)
                  if (itemstack1.isEmpty) {
                     enchantSlots.setItem(1, ItemStack.EMPTY)
                  }
               }
               p_75140_1_.awardStat(Stats.ENCHANT_ITEM)
               if (p_75140_1_ is ServerPlayerEntity) {
                  CriteriaTriggers.ENCHANTED_ITEM.trigger(p_75140_1_, itemstack2, i)
               }
               enchantSlots.setChanged()
               enchantmentSeed.set(p_75140_1_.enchantmentSeed)
               slotsChanged(enchantSlots)
               p_217003_6_.playSound(
                  null as PlayerEntity?,
                  p_217003_7_,
                  SoundEvents.ENCHANTMENT_TABLE_USE,
                  SoundCategory.BLOCKS,
                  1.0f,
                  p_217003_6_.random.nextFloat() * 0.1f + 0.9f
               )
            }
         }
         true
      }
   }

   private fun getEnchantmentList(p_178148_1_: ItemStack, p_178148_2_: Int, p_178148_3_: Int): List<EnchantmentData> {
      random.setSeed((enchantmentSeed.get() + p_178148_2_).toLong())
      val list = EnchantmentHelper.selectEnchantment(random, p_178148_1_, p_178148_3_, false)
      if (p_178148_1_.item === Items.BOOK && list.size > 1) {
         list.removeAt(random.nextInt(list.size))
      }
      return list
   }

   @get:OnlyIn(Dist.CLIENT)
   val goldCount: Int
      get() {
         val itemstack = enchantSlots.getItem(1)
         return if (itemstack.isEmpty) 0 else itemstack.count
      }

   @OnlyIn(Dist.CLIENT)
   fun getEnchantmentSeed(): Int {
      return Int()
   }

   override fun removed(p_75134_1_: PlayerEntity) {
      super.removed(p_75134_1_)
      access.execute { p_217004_2_: World?, p_217004_3_: BlockPos? ->
         clearContainer(
            p_75134_1_,
            p_75134_1_.level,
            enchantSlots
         )
      }
   }

   override fun stillValid(p_75145_1_: PlayerEntity): Boolean {
      return stillValid(access, p_75145_1_, Blocks.ENCHANTING_TABLE)
   }

   override fun quickMoveStack(p_82846_1_: PlayerEntity, p_82846_2_: Int): ItemStack {
      var itemstack = ItemStack.EMPTY
      val slot = slots[p_82846_2_]
      if (slot != null && slot.hasItem()) {
         val itemstack1 = slot.item
         itemstack = itemstack1.copy()
         if (p_82846_2_ == 0) {
            if (!moveItemStackTo(itemstack1, 2, 38, true)) {
               return ItemStack.EMPTY
            }
         } else if (p_82846_2_ == 1) {
            if (!moveItemStackTo(itemstack1, 2, 38, true)) {
               return ItemStack.EMPTY
            }
         } else if (itemstack1.item === Items.LAPIS_LAZULI) {
            if (!moveItemStackTo(itemstack1, 1, 2, true)) {
               return ItemStack.EMPTY
            }
         } else {
            if (slots[0].hasItem() || !slots[0].mayPlace(itemstack1)) {
               return ItemStack.EMPTY
            }
            val itemstack2 = itemstack1.copy()
            itemstack2.count = 1
            itemstack1.shrink(1)
            slots[0].set(itemstack2)
         }
         if (itemstack1.isEmpty) {
            slot.set(ItemStack.EMPTY)
         } else {
            slot.setChanged()
         }
         if (itemstack1.count == itemstack.count) {
            return ItemStack.EMPTY
         }
         slot.onTake(p_82846_1_, itemstack1)
      }
      return itemstack
   }

   init {
      addSlot(object : Slot(enchantSlots, 0, 15, 47) {
         override fun mayPlace(p_75214_1_: ItemStack): Boolean {
            return true
         }

         override fun getMaxStackSize(): Int {
            return 1
         }
      })
      addSlot(object : Slot(enchantSlots, 1, 35, 47) {
         override fun mayPlace(p_75214_1_: ItemStack): Boolean {
            return Tags.Items.GEMS_LAPIS.contains(p_75214_1_.item)
         }
      })
      for (i in 0..2) {
         for (j in 0..8) {
            addSlot(Slot(p_i50086_2_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18))
         }
      }
      for (k in 0..8) {
         addSlot(Slot(p_i50086_2_, k, 8 + k * 18, 142))
      }
      addDataSlot(IntReferenceHolder.shared(costs, 0))
      addDataSlot(IntReferenceHolder.shared(costs, 1))
      addDataSlot(IntReferenceHolder.shared(costs, 2))
      addDataSlot(enchantmentSeed).set(p_i50086_2_.player.enchantmentSeed)
      addDataSlot(IntReferenceHolder.shared(enchantClue, 0))
      addDataSlot(IntReferenceHolder.shared(enchantClue, 1))
      addDataSlot(IntReferenceHolder.shared(enchantClue, 2))
      addDataSlot(IntReferenceHolder.shared(levelClue, 0))
      addDataSlot(IntReferenceHolder.shared(levelClue, 1))
      addDataSlot(IntReferenceHolder.shared(levelClue, 2))
   }
}