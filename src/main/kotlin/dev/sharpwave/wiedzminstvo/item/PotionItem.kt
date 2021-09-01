package dev.sharpwave.wiedzminstvo.item

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.PotionItem as BasePotion

open class PotionItem(
    properties: Properties = Properties()
        .setNoRepair()
        .stacksTo(1)
        .tab(ModItemGroup.TAB_POTIONS)
) : BasePotion(properties) {

    override fun isFoil(p_77636_1_: ItemStack): Boolean {
        return (this as Item).isFoil(p_77636_1_)
    }
}