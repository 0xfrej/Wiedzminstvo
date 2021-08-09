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

    private val AETHER: RegistryObject<AetherItem> = ITEMS.register("aether") { AetherItem() }
    private val ALCHEMISTS_POWDER: RegistryObject<AlchemistsPowderItem> = ITEMS.register("alchemists_powder") { AlchemistsPowderItem() }
    private val ALCHEMY_PASTE: RegistryObject<AlchemyPasteItem> = ITEMS.register("alchemy_paste") { AlchemyPasteItem() }
    private val ALCOHEST: RegistryObject<AlcohestItem> = ITEMS.register("alcohest") { AlcohestItem() }
    private val BOTTOMLESS_CARAFE: RegistryObject<BottomlessCarafeItem> = ITEMS.register("bottomless_carafe") { BottomlessCarafeItem() }
    private val CALCIUM_EQUUM: RegistryObject<CalciumEquumItem> = ITEMS.register("calcium_equum") { CalciumEquumItem() }
    private val DARK_ESSENCE: RegistryObject<DarkEssenceItem> = ITEMS.register("dark_essence") { DarkEssenceItem() }
    private val DWARVEN_SPIRIT: RegistryObject<DwarvenSpiritItem> = ITEMS.register("dwarven_spirit") { DwarvenSpiritItem() }
    private val FIFTH_ESSENCE: RegistryObject<FifthEssenceItem> = ITEMS.register("fifth_essence") { FifthEssenceItem() }
    private val HYDRAGENUM: RegistryObject<HydragenumItem> = ITEMS.register("hydragenum") { HydragenumItem() }
    private val OPTIMA_MATER: RegistryObject<OptimaMaterItem> = ITEMS.register("optima_mater") { OptimaMaterItem() }
    private val PHOSPHORUS: RegistryObject<PhosphorusItem> = ITEMS.register("phosphorus") { PhosphorusItem() }
    private val QUEBRITH: RegistryObject<QuebrithItem> = ITEMS.register("quebrith") { QuebrithItem() }
    private val REBIS: RegistryObject<RebisItem> = ITEMS.register("rebis") { RebisItem() }
    private val RUBEDO: RegistryObject<RubedoItem> = ITEMS.register("rubedo") { RubedoItem() }
    private val SALTPETER: RegistryObject<SaltpeterItem> = ITEMS.register("saltpeter") { SaltpeterItem() }
    private val SULFUR: RegistryObject<SulfurItem> = ITEMS.register("sulfur") { SulfurItem() }
    private val VERMILLION: RegistryObject<VermillionItem> = ITEMS.register("vermillion") { VermillionItem() }
    private val VITRIOL: RegistryObject<VitriolItem> = ITEMS.register("vitriol") { VitriolItem() }
}