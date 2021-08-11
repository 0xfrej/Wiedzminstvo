package dev.sharpwave.wiedzminstvo

import dev.sharpwave.wiedzminstvo.client.managers.KeybindManager
import dev.sharpwave.wiedzminstvo.init.ModConfig
import dev.sharpwave.wiedzminstvo.init.ModRegistries
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.loading.FMLPaths
import net.minecraftforge.forgespi.language.IModInfo
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import net.minecraftforge.fml.config.ModConfig as FMLModConfig


@Mod(WiedzminstvoMod.MODID)
object WiedzminstvoMod {

    const val MODID: String = "wiedzminstvo"
    val info: IModInfo

    init {
        // Register configs
        ModLoadingContext.get().registerConfig(FMLModConfig.Type.SERVER, ModConfig.serverConfig)
        ModLoadingContext.get().registerConfig(FMLModConfig.Type.CLIENT, ModConfig.clientConfig)

        // Register the KDeferredRegister to the mod-specific event bus
        ModRegistries.register(MOD_BUS)

        // Load config files
        ModConfig.loadConfig(ModConfig.serverConfig, FMLPaths.CONFIGDIR.get().resolve("$MODID-server.toml").toString())
        ModConfig.loadConfig(ModConfig.clientConfig, FMLPaths.CONFIGDIR.get().resolve("$MODID-client.toml").toString())

        // usage of the KotlinEventBus
        MOD_BUS.addListener(::onClientSetup)

        info = ModLoadingContext.get().activeContainer.modInfo
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onClientSetup(event: FMLClientSetupEvent) {
        KeybindManager.init()
    }
}