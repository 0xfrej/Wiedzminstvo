package dev.sharpwave.wiedzminstvo.registry

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.item.crafting.MortarRecipe
import net.minecraft.item.crafting.CookingRecipeSerializer
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.item.crafting.IRecipeType
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.eventbus.KotlinEventBus
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object RecipeRegistry : IForgeRegistry {
    private val RECIPE: KDeferredRegister<IRecipeSerializer<*>> = KDeferredRegister(ForgeRegistries.RECIPE_SERIALIZERS, WiedzminstvoMod.MODID)

    override fun register(bus: KotlinEventBus) {
        RECIPE.register(bus)
    }

    val GRINDING = IRecipeType.register<MortarRecipe>("mortar_grinding")!!

    // TODO: Refactor factory
    val MORTAR_RECIPE by RECIPE.registerObject("mortar_grinding") { CookingRecipeSerializer({ location, name, ingredient, item, exp, grindTime -> MortarRecipe(location, name, ingredient, item, exp, grindTime) }, 3) }
    //val ALCHEMY_RECIPE by RECIPE.registerObject("alchemy") { SpecialRecipeSerializer({  }) }
}