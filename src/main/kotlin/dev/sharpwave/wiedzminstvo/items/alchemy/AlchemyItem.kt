package dev.sharpwave.wiedzminstvo.items.alchemy

import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.Rarity

open class AlchemyItem(properties: Properties) : Item(properties) {

    constructor() : this(Properties())

    // TODO: Add own alchemy tab as it's more specific than just brewing
    class Properties(rarity: Rarity = Rarity.COMMON) : Item.Properties() {
        init {
            setNoRepair()
            rarity(rarity)
            tab(ItemGroup.TAB_BREWING)
        }
    }
}