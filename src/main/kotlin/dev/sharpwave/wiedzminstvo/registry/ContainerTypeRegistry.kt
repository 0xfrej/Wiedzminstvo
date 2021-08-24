package dev.sharpwave.wiedzminstvo.registry

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.inventory.container.AlchemyContainer
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.ContainerType
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.eventbus.KotlinEventBus
import thedarkcolour.kotlinforforge.forge.KDeferredRegister
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate

object ContainerTypeRegistry : IForgeRegistry {
    private val CONTAINERS: KDeferredRegister<ContainerType<*>> =
        KDeferredRegister(ForgeRegistries.CONTAINERS, WiedzminstvoMod.MODID)

    override fun register(bus: KotlinEventBus) {
        CONTAINERS.register(bus)
    }

    val ALCHEMY by registerContainer("alchemy") { id: Int, inv: PlayerInventory -> AlchemyContainer(id, inv) }

    private fun <T : Container> registerContainer(
        name: String,
        factory: (Int, PlayerInventory) -> T
    ): ObjectHolderDelegate<ContainerType<T>> {
        return CONTAINERS.registerObject(name) { ContainerType<T>(factory) }
    }
}