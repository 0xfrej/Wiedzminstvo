package dev.sharpwave.wiedzminstvo.client.render

import dev.sharpwave.wiedzminstvo.registry.ItemRegistry
import net.minecraft.item.ItemStack
import net.minecraftforge.client.event.ColorHandlerEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
object ItemColors {

    @SubscribeEvent
    fun registerColors(event: ColorHandlerEvent.Item) {
        val itemColors = event.itemColors

        itemColors.register({ stack: ItemStack, tintIndex: Int ->
            // TODO: Write color handler
            // TOOD: Also check if it's getting fired
            -1
        }, ItemRegistry.ALCHEMY_POTION)
    }
}