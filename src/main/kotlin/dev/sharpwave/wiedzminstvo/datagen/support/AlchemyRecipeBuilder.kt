package dev.sharpwave.wiedzminstvo.datagen.support

import com.google.common.collect.Lists
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import dev.sharpwave.wiedzminstvo.registry.RecipeRegistry
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.ICriterionInstance
import net.minecraft.advancements.IRequirementsStrategy
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger
import net.minecraft.data.IFinishedRecipe
import net.minecraft.item.Item
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.item.crafting.Ingredient
import net.minecraft.tags.ITag
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation
import net.minecraft.util.registry.Registry
import java.util.function.Consumer

class AlchemyRecipeBuilder(resultItem: IItemProvider, count: Int) {
    private val result: Item
    private val count: Int
    private val ingredients: MutableList<Ingredient> = Lists.newArrayList()
    private val advancement = Advancement.Builder.advancement()
    private var group: String? = null

    init {
        result = resultItem.asItem()
        this.count = count
    }

    fun requires(tag: ITag<Item>): AlchemyRecipeBuilder {
        return this.requires(Ingredient.of(tag))
    }

    fun requires(item: IItemProvider): AlchemyRecipeBuilder {
        return this.requires(item, 1)
    }

    fun requires(item: IItemProvider, times: Int): AlchemyRecipeBuilder {
        for (i in 0 until times) {
            this.requires(Ingredient.of(item))
        }
        return this
    }

    fun requires(ingredient: Ingredient): AlchemyRecipeBuilder {
        return this.requires(ingredient, 1)
    }

    fun requires(ingredient: Ingredient, times: Int): AlchemyRecipeBuilder {
        for (i in 0 until times) {
            ingredients.add(ingredient)
        }
        return this
    }

    fun unlockedBy(name: String, instance: ICriterionInstance): AlchemyRecipeBuilder {
        advancement.addCriterion(name, instance)
        return this
    }

    fun group(group: String): AlchemyRecipeBuilder {
        this.group = group
        return this
    }

    fun save(consumer: Consumer<IFinishedRecipe>) {
        this.save(consumer, Registry.ITEM.getKey(result))
    }

    fun save(consumer: Consumer<IFinishedRecipe>, location: String) {
        val item = Registry.ITEM.getKey(result)
        check(ResourceLocation(location) != item) { "Shapeless Recipe $location should remove its 'save' argument" }
        this.save(consumer, ResourceLocation(location))
    }

    fun save(consumer: Consumer<IFinishedRecipe>, location: ResourceLocation) {
        ensureValid(location)
        advancement.parent(ResourceLocation("recipes/root"))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(location)).rewards(
                AdvancementRewards.Builder.recipe(location)
            ).requirements(IRequirementsStrategy.OR)
        consumer.accept(
            Result(
                location, result, count,
                (if (group == null) "" else group)!!,
                ingredients, advancement, ResourceLocation(
                    location.namespace, "recipes/" + result.itemCategory!!
                        .recipeFolderName + "/" + location.path
                )
            )
        )
    }

    private fun ensureValid(location: ResourceLocation) {
        check(advancement.criteria.isNotEmpty()) { "No way of obtaining recipe $location" }
    }

    class Result(
        private val id: ResourceLocation,
        private val result: Item,
        private val count: Int,
        private val group: String,
        private val ingredients: List<Ingredient>,
        private val advancement: Advancement.Builder,
        private val advancementId: ResourceLocation
    ) :
        IFinishedRecipe {
        override fun serializeRecipeData(json: JsonObject) {
            if (group.isNotEmpty())
                json.addProperty("group", group)

            val ingredientList = JsonArray()
            for (ingredient in ingredients) {
                ingredientList.add(ingredient.toJson())
            }
            json.add("ingredients", ingredientList)

            val result = JsonObject()
            result.addProperty("item", Registry.ITEM.getKey(this.result).toString())
            if (count > 1)
                result.addProperty("count", count)

            json.add("result", result)
        }

        override fun getType(): IRecipeSerializer<*> {
            return RecipeRegistry.ALCHEMY_SERIALIZER
        }

        override fun getId(): ResourceLocation {
            return id
        }

        override fun serializeAdvancement(): JsonObject {
            return advancement.serializeToJson()
        }

        override fun getAdvancementId(): ResourceLocation {
            return advancementId
        }
    }

    companion object {
        fun alchemy(resultItem: IItemProvider, count: Int): AlchemyRecipeBuilder {
            return AlchemyRecipeBuilder(resultItem, count)
        }

        fun alchemy(resultItem: IItemProvider): AlchemyRecipeBuilder {
            return alchemy(resultItem, 1)
        }
    }
}
