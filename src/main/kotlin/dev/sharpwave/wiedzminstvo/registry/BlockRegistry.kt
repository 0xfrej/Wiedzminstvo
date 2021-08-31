package dev.sharpwave.wiedzminstvo.registry

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.block.*
import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.eventbus.KotlinEventBus
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object BlockRegistry : IForgeRegistry {
    private val BLOCKS: KDeferredRegister<Block> = KDeferredRegister(ForgeRegistries.BLOCKS, WiedzminstvoMod.MODID)

    override fun register(bus: KotlinEventBus) {
        BLOCKS.register(bus)
    }

    val ALCHEMY_TABLE by BLOCKS.registerObject("alchemy_table") { AlchemyTableBlock(AbstractBlock.Properties.of(Material.STONE).strength(4F).sound(SoundType.STONE)) }
    val MORTAR by BLOCKS.registerObject("mortar") { MortarBlock(AbstractBlock.Properties.of(Material.STONE).strength(4F).sound(SoundType.STONE)) }
    val ARENARIA by BLOCKS.registerObject("arenaria") { AlchemyFlowerBlock(flowerProperties()) }
    val BEGGARTICK by BLOCKS.registerObject("beggartick_blossoms") { AlchemyFlowerBlock(flowerProperties()) }
    val BISON_GRASS by BLOCKS.registerObject("bison_grass") { AlchemyFlowerBlock(flowerProperties()) }
    val BLUE_LOTUS by BLOCKS.registerObject("blue_lotus") { TallAlchemyFlowerBlock(flowerProperties()) }
    val WINTER_CHERRY by BLOCKS.registerObject("winter_cherry") { TallAlchemyFlowerBlock(flowerProperties()) }
    val FOOLS_PARSLEY by BLOCKS.registerObject("fools_parsley") { AlchemyFlowerBlock(flowerProperties()) }
    val BERBERCANE by BLOCKS.registerObject("berbercane") { AlchemyFlowerBlock(flowerProperties()) }
    val CELANDINE by BLOCKS.registerObject("celandine") { AlchemyFlowerBlock(flowerProperties()) }
    val CORTINARIUS by BLOCKS.registerObject("cortinarius") { AlchemyMushroomBlock(mushroomProperties()) }

    private fun flowerProperties() : AbstractBlock.Properties {
        return AbstractBlock.Properties.of(Material.PLANT).noOcclusion().noCollission().instabreak().sound(SoundType.GRASS)
    }
    private fun mushroomProperties() : AbstractBlock.Properties {
        return AbstractBlock.Properties.of(Material.PLANT, MaterialColor.COLOR_BROWN).noCollission().randomTicks()
            .instabreak().sound(
                SoundType.GRASS
            ).lightLevel { 1 }
            .hasPostProcess { _, _, _ -> true }
    }
}