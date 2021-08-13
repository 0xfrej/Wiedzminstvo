package dev.sharpwave.wiedzminstvo.blocks

import net.minecraft.block.AbstractBlock
import net.minecraft.block.BushBlock
import net.minecraft.block.FlowerBlock
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.potion.Effect

object AlchemyFlowerBlock {
    fun make(properties: AbstractBlock.Properties? = null) : Bush {
        if (properties != null)
            return Bush(properties)
        return Bush()
    }
    fun make(effect: Effect, effectDuration: Int, properties: AbstractBlock.Properties? = null) : Flower {
        if (properties != null)
            return Flower(effect, effectDuration, properties)
        return Flower(effect, effectDuration)
    }

    class Flower(effect: Effect, effectDuration: Int, properties: Properties) : FlowerBlock(effect, effectDuration, properties) {
        constructor(effect: Effect, effectDuration: Int) :  this(effect, effectDuration, Properties.of(Material.PLANT).noOcclusion().noCollission().instabreak().sound(SoundType.GRASS))
    }

    class Bush(properties: Properties) : BushBlock(properties) {
        constructor() :  this(Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS))
    }
}