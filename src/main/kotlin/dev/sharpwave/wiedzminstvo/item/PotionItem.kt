package dev.sharpwave.wiedzminstvo.item

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.PotionItem as BasePotion

open class PotionItem(properties: Item.Properties) : BasePotion(properties) {

    constructor() : this(Properties())

    class Properties : Item.Properties() {
        init {
            setNoRepair()
            stacksTo(1)
            tab(ModItemGroup.TAB_POTIONS)
        }
    }

    override fun isFoil(p_77636_1_: ItemStack): Boolean {
        return (this as Item).isFoil(p_77636_1_)
    }
}