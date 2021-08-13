package dev.sharpwave.wiedzminstvo

import dev.sharpwave.wiedzminstvo.init.ModConfig
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.DistExecutor.SafeSupplier
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent
import net.minecraftforge.fml.loading.FMLPaths
import net.minecraftforge.fml.network.simple.SimpleChannel
import net.minecraftforge.forgespi.language.IModInfo
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import net.minecraftforge.fml.config.ModConfig as FMLModConfig


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
    lateinit var network: SimpleChannel
    val info: IModInfo

    var proxy: IProxy = DistExecutor.safeRunForDist(
        { SafeSupplier { ClientProxy() } }
    ) { SafeSupplier { CommonProxy() } }

    // the logger for our mod
    val logger: Logger = LogManager.getFormatterLogger("Wiedzminstvo")

    init {
        // Register configs
        ModLoadingContext.get().registerConfig(FMLModConfig.Type.SERVER, ModConfig.serverConfig)
        ModLoadingContext.get().registerConfig(FMLModConfig.Type.CLIENT, ModConfig.clientConfig)

        // Register the KDeferredRegister to the mod-specific event bus

        // Load config files
        ModConfig.loadConfig(ModConfig.serverConfig, FMLPaths.CONFIGDIR.get().resolve("$ID-server.toml").toString())
        ModConfig.loadConfig(ModConfig.clientConfig, FMLPaths.CONFIGDIR.get().resolve("$ID-client.toml").toString())

        // usage of the KotlinEventBus
        MOD_BUS.addListener(::onClientSetup)
        FORGE_BUS.addListener(::onServerAboutToStart)

        info = ModLoadingContext.get().getActiveContainer().getModInfo();
    }

    private fun onCommonSetup(event: FMLCommonSetupEvent) {

    }

    private fun onClientSetup(event: FMLClientSetupEvent) {
        //logger.log(Level.INFO, "Initializing client...")
    }

    private fun onServerAboutToStart(event: FMLServerAboutToStartEvent) {
        //logger.log(Level.INFO, "Server starting...")
    }
}