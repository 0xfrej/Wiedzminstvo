package dev.sharpwave.wiedzminstvo

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS

/**
 * Main mod class. Should be an `object` declaration annotated with `@Mod`.
 * The modid should be declared in this object and should match the modId entry
 * in mods.toml.
 *
 * An example for blocks is in the `blocks` package of this mod.
 */
@Mod(WiedzminstvoMod.ID)
object WiedzminstvoMod {
    // the modid of our mod
    const val ID: String = "wiedzminstvo"

    // the logger for our mod
    val LOGGER: Logger = LogManager.getLogger()

    init {
        LOGGER.log(Level.INFO, "Hello world!")

        // Register the KDeferredRegister to the mod-specific event bus

        // usage of the KotlinEventBus
        MOD_BUS.addListener(::onClientSetup)
        FORGE_BUS.addListener(::onServerAboutToStart)
    }

    private fun onCommonSetup(event: FMLCommonSetupEvent) {

    }

    private fun onClientSetup(event: FMLClientSetupEvent) {
        //.log(Level.INFO, "Initializing client...")
    }

    private fun onServerAboutToStart(event: FMLServerAboutToStartEvent) {
        //LOGGER.log(Level.INFO, "Server starting...")
    }
}