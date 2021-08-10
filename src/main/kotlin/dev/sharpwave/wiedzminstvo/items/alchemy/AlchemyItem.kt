package dev.sharpwave.wiedzminstvo.items.alchemy

import dev.sharpwave.wiedzminstvo.items.ModItemGroup
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.Rarity

open class AlchemyItem(properties: Properties) : Item(properties) {

    constructor() : this(Properties())

    constructor(rarity: Rarity) : this(Properties(rarity))

    class Properties(rarity: Rarity = Rarity.COMMON) : Item.Properties() {
        init {
            setNoRepair()
            rarity(rarity)
            tab(ModItemGroup.TAB_ALCHEMY)
        }
    }
}