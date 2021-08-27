package dev.sharpwave.wiedzminstvo.registry

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.alchemy.IngredientEffect
import dev.sharpwave.wiedzminstvo.block.AlchemyFlowerBlock
import dev.sharpwave.wiedzminstvo.block.AlchemyTableBlock
import dev.sharpwave.wiedzminstvo.block.MortarBlock
import dev.sharpwave.wiedzminstvo.block.TallAlchemyFlowerBlock
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.potion.Effects
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

    private fun flowerProperties() : AbstractBlock.Properties {
        return AbstractBlock.Properties.of(Material.PLANT).noOcclusion().noCollission().instabreak().sound(SoundType.GRASS)
    }
}