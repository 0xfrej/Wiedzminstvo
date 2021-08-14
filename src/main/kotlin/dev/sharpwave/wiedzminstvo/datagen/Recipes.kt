package dev.sharpwave.wiedzminstvo.datagen

import net.minecraft.data.DataGenerator
import net.minecraft.data.IFinishedRecipe
import net.minecraft.data.RecipeProvider
import java.util.function.Consumer

class Recipes(generator: DataGenerator) : RecipeProvider(generator) {
    override fun buildShapelessRecipes(consumer: Consumer<IFinishedRecipe>) {
        /**
         * example
            ShapedRecipeBuilder.shaped(Registration.TESTITEM.get())
            .pattern("xxx")
            .pattern(" s ")
            .pattern(" s ")
            .define('x', Tags.Items.BONES)
            .define('s', Items.STICK)
            .group("tutorial")
            .unlockedBy("sticks", InventoryChangeTrigger.TriggerInstance.hasItems(Items.STICK))
            .save(consumer);

            ShapedRecipeBuilder.shaped(Registration.GENERATOR.get())
            .pattern("iii")
            .pattern("iCi")
            .pattern("ccc")
            .define('i', Tags.Items.INGOTS_IRON)
            .define('C', Tags.Items.STORAGE_BLOCKS_COAL)
            .define('c', ItemTags.COALS)
            .group("tutorial")
            .unlockedBy("coals", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COAL))
            .save(consumer);

            ShapedRecipeBuilder.shaped(Registration.DEMO.get())
            .pattern("rir")
            .pattern("iri")
            .pattern("rir")
            .define('i', Tags.Items.INGOTS_IRON)
            .define('r', Tags.Items.DUSTS_REDSTONE)
            .group("tutorial")
            .unlockedBy("redstone", InventoryChangeTrigger.TriggerInstance.hasItems(Items.REDSTONE))
            .save(consumer);
         */
    }
}