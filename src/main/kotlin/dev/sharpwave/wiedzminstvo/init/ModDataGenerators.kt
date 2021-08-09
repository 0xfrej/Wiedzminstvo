package dev.sharpwave.wiedzminstvo.init

import dev.sharpwave.wiedzminstvo.datagen.*
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
object ModDataGenerators {
    fun gatherData(event: GatherDataEvent) {
        val generator = event.generator
        val existingFileHelper = event.existingFileHelper

        //if (event.includeServer()) {
            //generator.addProvider(Recipes(generator))
            //generator.addProvider(LootTables(generator))
            //generator.addProvider(Tags(generator, event.existingFileHelper))
        //}

        if (event.includeClient()) {
            generator.addProvider(BlockStates(generator))
            generator.addProvider(Items(generator, existingFileHelper))
        }
    }
}