package dev.sharpwave.wiedzminstvo.items

import net.minecraft.item.Item
import net.minecraft.item.Rarity

class BottomlessCarafeItem(properties: Item.Properties) : Item(properties) {
    constructor() : this(Properties())

    class Properties : Item.Properties() {
        init {
            setNoRepair()
            rarity(Rarity.EPIC)
            tab(ModItemGroup.TAB_ALCHEMY)
        }
    }
}