package dev.sharpwave.wiedzminstvo.registries

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.items.AlchemyItem
import dev.sharpwave.wiedzminstvo.items.BottomlessCarafeItem
import dev.sharpwave.wiedzminstvo.utils.DatagenHelpers.getRegistryPath
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.eventbus.KotlinEventBus
import thedarkcolour.kotlinforforge.forge.KDeferredRegister
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate

object ItemRegistry : IForgeRegistry {
    private val ITEMS : KDeferredRegister<Item> = KDeferredRegister(ForgeRegistries.ITEMS, WiedzminstvoMod.MODID)

    override fun register(bus: KotlinEventBus) {
        ITEMS.register(bus)
    }

    val BOTTOMLESS_CARAFE by ITEMS.registerObject("bottomless_carafe") { BottomlessCarafeItem() }
    val ALCHEMISTS_POWDER by ITEMS.registerObject("alchemists_powder") { AlchemyItem() }
    val AETHER by ITEMS.registerObject("aether") { AlchemyItem() }
    val ALCHEMY_PASTE by ITEMS.registerObject("alchemy_paste") { AlchemyItem() }
    val ALCOHEST by ITEMS.registerObject("alcohest") { AlchemyItem() }
    val CALCIUM_EQUUM by ITEMS.registerObject("calcium_equum") { AlchemyItem() }
    val DARK_ESSENCE by ITEMS.registerObject("dark_essence") { AlchemyItem() }
    val DWARVEN_SPIRIT by ITEMS.registerObject("dwarven_spirit") { AlchemyItem() }
    val FIFTH_ESSENCE by ITEMS.registerObject("fifth_essence") { AlchemyItem() }
    val HYDRAGENUM by ITEMS.registerObject("hydragenum") { AlchemyItem() }
    val OPTIMA_MATER by ITEMS.registerObject("optima_mater") { AlchemyItem() }
    val PHOSPHORUS by ITEMS.registerObject("phosphorus") { AlchemyItem() }
    val QUEBRITH by ITEMS.registerObject("quebrith") { AlchemyItem() }
    val REBIS by ITEMS.registerObject("rebis") { AlchemyItem() }
    val RUBEDO by ITEMS.registerObject("rubedo") { AlchemyItem() }
    val SALTPETER by ITEMS.registerObject("saltpeter") { AlchemyItem() }
    val SULFUR by ITEMS.registerObject("sulfur") { AlchemyItem() }
    val VERMILION by ITEMS.registerObject("vermilion") { AlchemyItem() }
    val VITRIOL by ITEMS.registerObject("vitriol") { AlchemyItem() }
    val ARENARIA by registerBlock(BlockRegistry.ARENARIA)
    val BEGGARTICK by registerBlock(BlockRegistry.BEGGARTICK)

    private fun registerBlock(block: Block): ObjectHolderDelegate<BlockItem> {
        return ITEMS.registerObject(getRegistryPath(block)) { BlockItem(block, Item.Properties()) }
    }

    private fun registerBlock(block: Block, tab: ItemGroup): ObjectHolderDelegate<BlockItem> {
        return ITEMS.registerObject(getRegistryPath(block)) { BlockItem(block, Item.Properties().tab(tab)) }
    }
}