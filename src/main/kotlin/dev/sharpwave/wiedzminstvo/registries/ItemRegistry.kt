package dev.sharpwave.wiedzminstvo.registries

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.items.alchemy.*
import net.minecraft.item.Item
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object ItemRegistry {
    private val ITEMS : DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, WiedzminstvoMod.MODID)

    init {
        ITEMS.register(FMLJavaModLoadingContext.get().modEventBus)
    }

    val AETHER: RegistryObject<AetherItem> = ITEMS.register("aether") { AetherItem() }
    val ALCHEMISTS_POWDER: RegistryObject<AlchemistsPowderItem> = ITEMS.register("alchemists_powder") { AlchemistsPowderItem() }
    val ALCHEMY_PASTE: RegistryObject<AlchemyPasteItem> = ITEMS.register("alchemy_paste") { AlchemyPasteItem() }
    val ALCOHEST: RegistryObject<AlcohestItem> = ITEMS.register("alcohest") { AlcohestItem() }
    val BOTTOMLESS_CARAFE: RegistryObject<BottomlessCarafeItem> = ITEMS.register("bottomless_carafe") { BottomlessCarafeItem() }
    val CALCIUM_EQUUM: RegistryObject<CalciumEquumItem> = ITEMS.register("calcium_equum") { CalciumEquumItem() }
    val DARK_ESSENCE: RegistryObject<DarkEssenceItem> = ITEMS.register("dark_essence") { DarkEssenceItem() }
    val DWARVEN_SPIRIT: RegistryObject<DwarvenSpiritItem> = ITEMS.register("dwarven_spirit") { DwarvenSpiritItem() }
    val FIFTH_ESSENCE: RegistryObject<FifthEssenceItem> = ITEMS.register("fifth_essence") { FifthEssenceItem() }
    val HYDRAGENUM: RegistryObject<HydragenumItem> = ITEMS.register("hydragenum") { HydragenumItem() }
    val OPTIMA_MATER: RegistryObject<OptimaMaterItem> = ITEMS.register("optima_mater") { OptimaMaterItem() }
    val PHOSPHORUS: RegistryObject<PhosphorusItem> = ITEMS.register("phosphorus") { PhosphorusItem() }
    val QUEBRITH: RegistryObject<QuebrithItem> = ITEMS.register("quebrith") { QuebrithItem() }
    val REBIS: RegistryObject<RebisItem> = ITEMS.register("rebis") { RebisItem() }
    val RUBEDO: RegistryObject<RubedoItem> = ITEMS.register("rubedo") { RubedoItem() }
    val SALTPETER: RegistryObject<SaltpeterItem> = ITEMS.register("saltpeter") { SaltpeterItem() }
    val SULFUR: RegistryObject<SulfurItem> = ITEMS.register("sulfur") { SulfurItem() }
    val VERMILLION: RegistryObject<VermillionItem> = ITEMS.register("vermillion") { VermillionItem() }
    val VITRIOL: RegistryObject<VitriolItem> = ITEMS.register("vitriol") { VitriolItem() }
}