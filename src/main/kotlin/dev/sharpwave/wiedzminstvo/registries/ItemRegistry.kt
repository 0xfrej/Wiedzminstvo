package dev.sharpwave.wiedzminstvo.registries

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.items.alchemy.*
import net.minecraft.item.Item
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.eventbus.KotlinEventBus
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object ItemRegistry : IForgeRegistry {
    private val ITEMS : KDeferredRegister<Item> = KDeferredRegister(ForgeRegistries.ITEMS, WiedzminstvoMod.MODID)

    override fun register(bus: KotlinEventBus) {
        ITEMS.register(bus)
    }

    val AETHER by ITEMS.registerObject("aether") { AetherItem() }
    val ALCHEMISTS_POWDER by ITEMS.registerObject("alchemists_powder") { AlchemistsPowderItem() }
    val ALCHEMY_PASTE by ITEMS.registerObject("alchemy_paste") { AlchemyPasteItem() }
    val ALCOHEST by ITEMS.registerObject("alcohest") { AlcohestItem() }
    val BOTTOMLESS_CARAFE by ITEMS.registerObject("bottomless_carafe") { BottomlessCarafeItem() }
    val CALCIUM_EQUUM by ITEMS.registerObject("calcium_equum") { CalciumEquumItem() }
    val DARK_ESSENCE by ITEMS.registerObject("dark_essence") { DarkEssenceItem() }
    val DWARVEN_SPIRIT by ITEMS.registerObject("dwarven_spirit") { DwarvenSpiritItem() }
    val FIFTH_ESSENCE by ITEMS.registerObject("fifth_essence") { FifthEssenceItem() }
    val HYDRAGENUM by ITEMS.registerObject("hydragenum") { HydragenumItem() }
    val OPTIMA_MATER by ITEMS.registerObject("optima_mater") { OptimaMaterItem() }
    val PHOSPHORUS by ITEMS.registerObject("phosphorus") { PhosphorusItem() }
    val QUEBRITH by ITEMS.registerObject("quebrith") { QuebrithItem() }
    val REBIS by ITEMS.registerObject("rebis") { RebisItem() }
    val RUBEDO by ITEMS.registerObject("rubedo") { RubedoItem() }
    val SALTPETER by ITEMS.registerObject("saltpeter") { SaltpeterItem() }
    val SULFUR by ITEMS.registerObject("sulfur") { SulfurItem() }
    val VERMILION by ITEMS.registerObject("vermilion") { VermilionItem() }
    val VITRIOL by ITEMS.registerObject("vitriol") { VitriolItem() }

}