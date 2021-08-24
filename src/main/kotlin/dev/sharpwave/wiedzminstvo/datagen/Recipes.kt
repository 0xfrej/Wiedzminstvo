package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.datagen.support.AlchemyRecipeBuilder
import dev.sharpwave.wiedzminstvo.registry.ItemRegistry
import dev.sharpwave.wiedzminstvo.registry.RecipeRegistry
import net.minecraft.advancements.criterion.InventoryChangeTrigger
import net.minecraft.data.*
import net.minecraft.item.Items
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.IItemProvider
import java.util.function.Consumer

class Recipes(generator: DataGenerator) : RecipeProvider(generator) {
    override fun buildShapelessRecipes(consumer: Consumer<IFinishedRecipe>) {
        ShapedRecipeBuilder.shaped(ItemRegistry.ALCHEMY_TABLE).pattern("sss").pattern(" s ").pattern("sss").define('s', Items.STONE).unlockedBy("stone", InventoryChangeTrigger.Instance.hasItems(Items.STONE)).save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.MORTAR).pattern("   ").pattern("s s").pattern("sss").define('s', Items.STONE).unlockedBy("stone", InventoryChangeTrigger.Instance.hasItems(Items.STONE)).save(consumer);

        grinding(ItemRegistry.ARENARIA, ItemRegistry.GROUND_ARENARIA, .5F).unlockedBy("arenaria", InventoryChangeTrigger.Instance.hasItems(ItemRegistry.ARENARIA)).save(consumer)
        grinding(ItemRegistry.BEGGARTICK, ItemRegistry.GROUND_BEGGARTICK, .5F).unlockedBy("beggartick", InventoryChangeTrigger.Instance.hasItems(ItemRegistry.BEGGARTICK)).save(consumer)
        grinding(ItemRegistry.BISON_GRASS, ItemRegistry.GROUND_BISON_GRASS, .5F).unlockedBy("bison_grass", InventoryChangeTrigger.Instance.hasItems(ItemRegistry.BISON_GRASS)).save(consumer)
        grinding(ItemRegistry.BLUE_LOTUS, ItemRegistry.GROUND_BLUE_LOTUS, .5F).unlockedBy("blue_lotus", InventoryChangeTrigger.Instance.hasItems(ItemRegistry.BLUE_LOTUS)).save(consumer)
        grinding(ItemRegistry.WINTER_CHERRY, ItemRegistry.GROUND_WINTER_CHERRY, .5F).unlockedBy("winter_cherry", InventoryChangeTrigger.Instance.hasItems(ItemRegistry.WINTER_CHERRY)).save(consumer)

        AlchemyRecipeBuilder.alchemy(ItemRegistry.ALCHEMY_PASTE).requires(ItemRegistry.GROUND_ARENARIA).requires(ItemRegistry.PORK_FAT).unlockedBy("pork_fat", InventoryChangeTrigger.Instance.hasItems(ItemRegistry.PORK_FAT)).unlockedBy("ground_arenaria", InventoryChangeTrigger.Instance.hasItems(ItemRegistry.GROUND_ARENARIA)).save(consumer)
    }

    private fun grinding(input: IItemProvider, output: IItemProvider, experience: Float, grindingCycles: Int = 3): CookingRecipeBuilder {
        return CookingRecipeBuilder.cooking(Ingredient.of(input), output, experience, grindingCycles, RecipeRegistry.GRINDING_SERIALIZER)
    }
}