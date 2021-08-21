package dev.sharpwave.wiedzminstvo.tag

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod.MODID
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.tags.ITag.INamedTag
import net.minecraft.tags.TagRegistry
import net.minecraft.tags.TagRegistryManager
import net.minecraft.util.ResourceLocation

// TODO: Abstract this away
object ItemTags {
    @Suppress("UNCHECKED_CAST")
    private val HELPER = TagRegistryManager.get(ResourceLocation("item")) as TagRegistry<Item>

    private fun bind(group: String): INamedTag<Item> {
        return HELPER.bind("$MODID:$group")
    }

    val ALCHEMY_INGREDIENTS = bind("alchemy_ingredients")
    val BASE_ALCHEMY_POTIONS = bind("alchemy_base_potions")
}