package dev.sharpwave.wiedzminstvo.item

import dev.sharpwave.wiedzminstvo.alchemy.IPotionTier
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.PotionItem as BasePotion

open class FusionIngredientItem(
    val tier: IPotionTier,
    properties: Properties = Properties()
        .tab(ModItemGroup.TAB_ALCHEMY)
) : Item(properties) {

}