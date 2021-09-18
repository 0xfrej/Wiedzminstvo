package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.locale.GeneralStrings
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.LanguageProvider

class Language(generator: DataGenerator) : LanguageProvider(generator, WiedzminstvoMod.MODID, "en_US") {
    override fun addTranslations() {
        add(GeneralStrings.KEY_MODID_CATEGORY, "Wiedzminstvo")

        add("itemGroup.wiedzminstvo_alchemy", "Wiedzminstvo - Alchemy")
        add("itemGroup.wiedzminstvo_potions", "Wiedzminstvo - Potions")
        add("itemGroup.wiedzminstvo_equipment", "Wiedzminstvo - Armor & Swords")
        add("itemGroup.wiedzminstvo_misc", "Wiedzminstvo - Misc")

    }
}