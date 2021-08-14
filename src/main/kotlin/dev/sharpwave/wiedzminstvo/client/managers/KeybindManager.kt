package dev.sharpwave.wiedzminstvo.client.managers

import dev.sharpwave.wiedzminstvo.config.HorseConfig
import dev.sharpwave.wiedzminstvo.locale.GeneralStrings
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.loading.FMLEnvironment
import org.lwjgl.glfw.GLFW


object KeybindManager {
    var setHorse: KeyBinding? = null
    var callHorse: KeyBinding? = null
    var showStats: KeyBinding? = null

    fun init() {
        if (FMLEnvironment.dist.isClient) {
            setHorse = KeyBinding(GeneralStrings.KEY_SET_HORSE_DESC, GLFW.GLFW_KEY_P, GeneralStrings.KEY_MODID_CATEGORY)
            callHorse = KeyBinding(GeneralStrings.KEY_CALL_HORSE_DESC, GLFW.GLFW_KEY_V, GeneralStrings.KEY_MODID_CATEGORY)
            showStats = KeyBinding(GeneralStrings.KEY_SHOW_HORSE_STATS_DESC, GLFW.GLFW_KEY_K, GeneralStrings.KEY_MODID_CATEGORY)
            ClientRegistry.registerKeyBinding(setHorse)
            ClientRegistry.registerKeyBinding(callHorse)
            if (HorseConfig.enableStatsViewer?.get() == true) ClientRegistry.registerKeyBinding(showStats)
        }
    }
}