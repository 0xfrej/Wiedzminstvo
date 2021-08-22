package dev.sharpwave.wiedzminstvo.registry

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.tileentity.AlchemyTableTileEntity
import dev.sharpwave.wiedzminstvo.tileentity.MortarPestleTileEntity
import net.minecraft.block.Block
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.eventbus.KotlinEventBus
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object TileEntityRegistry : IForgeRegistry {
    private val ENTITY: KDeferredRegister<TileEntityType<*>> = KDeferredRegister(ForgeRegistries.TILE_ENTITIES, WiedzminstvoMod.MODID)

    override fun register(bus: KotlinEventBus) {
        ENTITY.register(bus)
    }

    val ALCHEMY_TABLE by ENTITY.registerObject("alchemy_table") { buildTEType({ AlchemyTableTileEntity() }, BlockRegistry.ALCHEMY_TABLE) }
    val PESTLE by ENTITY.registerObject("pestle") { buildTEType({ MortarPestleTileEntity() }, BlockRegistry.MORTAR) }

    private fun <T: TileEntity> buildTEType(factory: () -> T, vararg validBlocks: Block): TileEntityType<T> {
        return TileEntityType.Builder.of(factory, *validBlocks).build(null)
    }
}