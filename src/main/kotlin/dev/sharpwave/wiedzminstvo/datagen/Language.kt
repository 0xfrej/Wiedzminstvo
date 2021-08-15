package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.locale.GeneralStrings
import dev.sharpwave.wiedzminstvo.locale.HorseStrings
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.LanguageProvider

class Language(generator: DataGenerator) : LanguageProvider(generator, WiedzminstvoMod.MODID, "en_US") {
    override fun addTranslations() {
        add(GeneralStrings.KEY_MODID_CATEGORY, "Wiedzminstvo")
        add(GeneralStrings.KEY_SET_HORSE_DESC, "Set Horse")
        add(GeneralStrings.KEY_CALL_HORSE_DESC, "Call Horse")
        add(GeneralStrings.KEY_SHOW_HORSE_STATS_DESC, "Show Horse Stats")

        add(HorseStrings.HORSE_ERROR_DIM, "You can't call your horse in this dimension!")
        add(HorseStrings.HORSE_ERROR_RIDING, "You can't be riding while calling your horse!")
        add(HorseStrings.HORSE_ERROR_NO_HORSE, "You currently don't have a personal Horse!")
        add(HorseStrings.HORSE_ERROR_NOT_RIDING, "You need to be riding a horse to do this!")
        add(HorseStrings.HORSE_ERROR_ALREADY_OWNED, "This horse already has an owner!")
        add(HorseStrings.HORSE_ERROR_ALREADY_PERSONAL, "This is already your personal horse!")
        add(HorseStrings.HORSE_ERROR_AREA_BANNED, "You can't call your horse in this area!")
        add(HorseStrings.HORSE_ERROR_RANGE, "Your horse is too far away to hear you!")
        add(HorseStrings.HORSE_ERROR_NO_SPACE, "There is not enough space here to call your horse!")
        add(HorseStrings.HORSE_SUCCESS_SET_HORSE, "This is now your personal horse!")
        add(HorseStrings.HORSE_ALERT_DEATH, "Your personal horse just died!")
        add(HorseStrings.HORSE_ALERT_OFFLINE_DEATH, "Your personal horse died while you were offline!")

        add("itemGroup.wiedzminstvo_alchemy", "Wiedzminstvo - Alchemy")
        add("itemGroup.wiedzminstvo_potions", "Wiedzminstvo - Potions")
        add("itemGroup.wiedzminstvo_equipment", "Wiedzminstvo - Armor & Swords")
        add("itemGroup.wiedzminstvo_misc", "Wiedzminstvo - Misc")

    }
}