package dev.sharpwave.wiedzminstvo.entity

import net.minecraft.entity.EntityType
import net.minecraft.tags.ITag.INamedTag
import net.minecraft.tags.ITagCollection
import net.minecraft.tags.ITagCollectionSupplier
import net.minecraft.tags.TagRegistryManager
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.Tags.IOptionalNamedTag
import java.util.function.Supplier

object EntityTypeTags {
    private val HELPER = TagRegistryManager.create(
        ResourceLocation("entity_type")
    ) { obj: ITagCollectionSupplier -> obj.entityTypes }
    val HORSES = bind("horses")
    fun bind(group: String): INamedTag<EntityType<*>> {
        return HELPER.bind(group)
    }

    @JvmOverloads
    fun createOptional(
        name: ResourceLocation,
        defaults: Set<Supplier<EntityType<*>>>? = null
    ): IOptionalNamedTag<EntityType<*>> {
        return HELPER.createOptional(name, defaults)
    }

    val allTags: ITagCollection<EntityType<*>>
        get() = HELPER.allTags
    val wrappers: List<INamedTag<EntityType<*>>>
        get() = HELPER.wrappers
}