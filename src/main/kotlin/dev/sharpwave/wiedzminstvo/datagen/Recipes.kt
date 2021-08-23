package dev.sharpwave.wiedzminstvo.datagen

import dev.sharpwave.wiedzminstvo.registry.ItemRegistry
import net.minecraft.advancements.criterion.InventoryChangeTrigger
import net.minecraft.data.*
import net.minecraft.item.Items
import java.util.function.Consumer

class Recipes(generator: DataGenerator) : RecipeProvider(generator) {
    override fun buildShapelessRecipes(consumer: Consumer<IFinishedRecipe>) {
        ShapedRecipeBuilder.shaped(ItemRegistry.ALCHEMY_TABLE).pattern("sss").pattern(" s ").pattern("sss").define('s', Items.STONE).group("alchemy").unlockedBy("stone", InventoryChangeTrigger.Instance.hasItems(Items.STONE)).save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.MORTAR).pattern("   ").pattern("s s").pattern("sss").define('s', Items.STONE).group("alchemy").unlockedBy("stone", InventoryChangeTrigger.Instance.hasItems(Items.STONE)).save(consumer);
    }
}