package dev.sharpwave.wiedzminstvo

import dev.sharpwave.wiedzminstvo.init.ModConfig
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.DistExecutor.SafeSupplier
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.loading.FMLPaths
import net.minecraftforge.fml.network.simple.SimpleChannel
import net.minecraftforge.forgespi.language.IModInfo
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import net.minecraftforge.fml.config.ModConfig as FMLModConfig


/**
 * Main mod class. Should be an `object` declaration annotated with `@Mod`.
 * The modid should be declared in this object and should match the modId entry
 * in mods.toml.
 *
 * An example for blocks is in the `blocks` package of this mod.
 */
@Mod(WiedzminstvoMod.MODID)
object WiedzminstvoMod {

    const val MODID: String = "wiedzminstvo"
    lateinit var mainNetwork: SimpleChannel
    val info: IModInfo

    var proxy: IProxy = DistExecutor.safeRunForDist(
        { SafeSupplier { ClientProxy() } }
    ) { SafeSupplier { CommonProxy() } }

    // the logger for our mod
    //TODO: Replace usages for Logger
    val logger: Logger = LogManager.getFormatterLogger("Wiedzminstvo")

    init {
        // Register configs
        ModLoadingContext.get().registerConfig(FMLModConfig.Type.SERVER, ModConfig.serverConfig)
        ModLoadingContext.get().registerConfig(FMLModConfig.Type.CLIENT, ModConfig.clientConfig)

        // Register the KDeferredRegister to the mod-specific event bus

        // Load config files
        ModConfig.loadConfig(ModConfig.serverConfig, FMLPaths.CONFIGDIR.get().resolve("$MODID-server.toml").toString())
        ModConfig.loadConfig(ModConfig.clientConfig, FMLPaths.CONFIGDIR.get().resolve("$MODID-client.toml").toString())

        // usage of the KotlinEventBus
        /*MOD_BUS.addListener(::onClientSetup)
        FORGE_BUS.addListener(::onServerAboutToStart)
        MOD_BUS.addListener(::onCommonSetup)*/

        info = ModLoadingContext.get().activeContainer.modInfo;
    }

    /*private fun onCommonSetup(event: FMLCommonSetupEvent) {

    }*/

    /*private fun onClientSetup(event: FMLClientSetupEvent) {

    }*/

    /*private fun onServerAboutToStart(event: FMLServerAboutToStartEvent) {

    }*/
}