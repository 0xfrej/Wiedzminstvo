package dev.sharpwave.wiedzminstvo.init

import dev.sharpwave.wiedzminstvo.datagen.*
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
object ModDataGenerators {
    @SubscribeEvent
    fun gatherData(event: GatherDataEvent) {
        val generator = event.generator
        val existingFileHelper = event.existingFileHelper

        if (event.includeServer()) {
            //generator.addProvider(Recipes(generator))
            generator.addProvider(BlockLootTables(generator))
            generator.addProvider(EntityTags(generator, event.existingFileHelper))
        }

        if (event.includeClient()) {
            generator.addProvider(BlockStates(generator, existingFileHelper))
            generator.addProvider(Items(generator, existingFileHelper))
            generator.addProvider(Blocks(generator, existingFileHelper))
            generator.addProvider(Language(generator))
        }
    }
}