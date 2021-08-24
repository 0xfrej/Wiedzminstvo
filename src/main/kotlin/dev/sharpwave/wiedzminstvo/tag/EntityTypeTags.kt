package dev.sharpwave.wiedzminstvo.tag

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod.MODID
import net.minecraft.entity.EntityType
import net.minecraft.tags.ITag.INamedTag
import net.minecraft.tags.TagRegistry
import net.minecraft.tags.TagRegistryManager
import net.minecraft.util.ResourceLocation

object EntityTypeTags {
    @Suppress("UNCHECKED_CAST")
    private val HELPER = TagRegistryManager.get(ResourceLocation("entity_type")) as TagRegistry<EntityType<*>>

    private fun bind(group: String): INamedTag<EntityType<*>> {
        return HELPER.bind("$MODID:$group")
    }

    val HORSES = bind("horses")
    val PIGLIKE = bind("piglike")
}