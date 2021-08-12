package dev.sharpwave.wiedzminstvo.registries

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.OreBlock
import net.minecraft.block.material.Material
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.eventbus.KotlinEventBus
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object BlockRegistry : IForgeRegistry {

    private val BLOCKS : KDeferredRegister<Block> = KDeferredRegister(ForgeRegistries.BLOCKS, WiedzminstvoMod.MODID)

    override fun register(bus: KotlinEventBus) {
        BLOCKS.register(bus)
    }

    val AETHER by BLOCKS.registerObject("aether_ore") { OreBlock(AbstractBlock.Properties.of(Material.STONE)) }
    val CALCIUM_EQUUM by BLOCKS.registerObject("calcium_equum_ore") { OreBlock(AbstractBlock.Properties.of(Material.STONE)) }
    val HYDRAGENUM by BLOCKS.registerObject("hydragenum_ore") { OreBlock(AbstractBlock.Properties.of(Material.STONE)) }
    val OPTIMA_MATER by BLOCKS.registerObject("optima_mater_ore") { OreBlock(AbstractBlock.Properties.of(Material.STONE)) }
    val QUEBRITH by BLOCKS.registerObject("quebrith_ore") { OreBlock(AbstractBlock.Properties.of(Material.STONE)) }
    val REBIS by BLOCKS.registerObject("rebis_ore") { OreBlock(AbstractBlock.Properties.of(Material.STONE)) }
    val RUBEDO by BLOCKS.registerObject("rubedo_ore") { OreBlock(AbstractBlock.Properties.of(Material.STONE)) }
    val SALTPETER by BLOCKS.registerObject("saltpeter_ore") { OreBlock(AbstractBlock.Properties.of(Material.STONE)) }
    val SULFUR by BLOCKS.registerObject("sulfur_ore") { OreBlock(AbstractBlock.Properties.of(Material.STONE)) }
    val VERMILION by BLOCKS.registerObject("vermilion_ore") { OreBlock(AbstractBlock.Properties.of(Material.STONE)) }
    val VITRIOL by BLOCKS.registerObject("vitriol_ore") { OreBlock(AbstractBlock.Properties.of(Material.STONE)) }
}