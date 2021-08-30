package dev.sharpwave.wiedzminstvo.registry

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.item.crafting.AlchemyRecipe
import dev.sharpwave.wiedzminstvo.item.crafting.AlchemyRecipeSerializer
import dev.sharpwave.wiedzminstvo.item.crafting.MortarRecipe
import net.minecraft.item.crafting.CookingRecipeSerializer
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.item.crafting.IRecipeType
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.eventbus.KotlinEventBus
import thedarkcolour.kotlinforforge.forge.KDeferredRegister


object RecipeRegistry : IForgeRegistry {
    private val RECIPE: KDeferredRegister<IRecipeSerializer<*>> =
        KDeferredRegister(ForgeRegistries.RECIPE_SERIALIZERS, WiedzminstvoMod.MODID)

    override fun register(bus: KotlinEventBus) {
        RECIPE.register(bus)
    }

    val GRINDING: IRecipeType<MortarRecipe> = IRecipeType.register("${WiedzminstvoMod.MODID}:mortar_grinding")
    val ALCHEMY: IRecipeType<AlchemyRecipe> = IRecipeType.register("${WiedzminstvoMod.MODID}:alchemy")

    // TODO: Refactor factory
    val GRINDING_SERIALIZER by RECIPE.registerObject("mortar_grinding") { CookingRecipeSerializer({ location, name, ingredient, item, exp, grindTime -> MortarRecipe(location, name, ingredient, item, exp, grindTime) }, 3) }
    val ALCHEMY_SERIALIZER by RECIPE.registerObject("alchemy") { AlchemyRecipeSerializer() }
}