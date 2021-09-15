package dev.sharpwave.wiedzminstvo.registry

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.block.*
import dev.sharpwave.wiedzminstvo.block.FlowerBlock
import dev.sharpwave.wiedzminstvo.block.MushroomBlock
import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
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
    val ARENARIA by BLOCKS.registerObject("arenaria") { FlowerBlock(flowerProperties()) }
    val BEGGARTICK by BLOCKS.registerObject("beggartick_blossoms") { FlowerBlock(flowerProperties()) }
    val PUFFBALL by BLOCKS.registerObject("puffball") { FlowerBlock(flowerProperties()) }
    val BISON_GRASS by BLOCKS.registerObject("bison_grass") { FlowerBlock(flowerProperties()) }
    val BLUE_LOTUS by BLOCKS.registerObject("blue_lotus") { GrowableTallFlowerBlock(growableFlowerProperties(), GrowableConditions.NONE) }
    val WINTER_CHERRY by BLOCKS.registerObject("winter_cherry") { GrowableTallFlowerBlock(growableFlowerProperties(), GrowableConditions.NONE) }
    val FOOLS_PARSLEY by BLOCKS.registerObject("fools_parsley") { GrowableFlowerBlock(growableFlowerProperties(), GrowableConditions.NONE) }
    val BERBERCANE by BLOCKS.registerObject("berbercane") { GrowableFlowerBlock(growableFlowerProperties(), GrowableConditions.NONE) }
    val CELANDINE by BLOCKS.registerObject("celandine") { GrowableFlowerBlock(growableFlowerProperties(), GrowableConditions.NONE) }
    val CORTINARIUS by BLOCKS.registerObject("cortinarius") { MushroomBlock(mushroomProperties()) }
    val BALLISEFRUIT by BLOCKS.registerObject("ballisefruit") { GrowableFlowerBlock(growableFlowerProperties(), GrowableConditions.NONE) }

    private fun flowerProperties() : AbstractBlock.Properties {
        return AbstractBlock.Properties.of(Material.PLANT).noOcclusion().noCollission().instabreak().sound(SoundType.GRASS)
    }
    private fun growableFlowerProperties() : AbstractBlock.Properties {
        return AbstractBlock.Properties.of(Material.PLANT).noOcclusion().noCollission().instabreak().randomTicks().sound(SoundType.GRASS)
    }
    private fun mushroomProperties() : AbstractBlock.Properties {
        return AbstractBlock.Properties.of(Material.PLANT, MaterialColor.COLOR_BROWN).noCollission().randomTicks()
            .instabreak().sound(
                SoundType.GRASS
            ).lightLevel { 1 }
            .hasPostProcess { _, _, _ -> true }
    }
}