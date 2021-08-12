package dev.sharpwave.wiedzminstvo.client.managers

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.config.HorseConfig
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
            setHorse = KeyBinding("key.sethorse.desc", GLFW.GLFW_KEY_P, "key.${WiedzminstvoMod.ID}.category")
            callHorse = KeyBinding("key.callhorse.desc", GLFW.GLFW_KEY_V, "key.${WiedzminstvoMod.ID}.category")
            showStats = KeyBinding("key.showstats.desc", GLFW.GLFW_KEY_K, "key.${WiedzminstvoMod.ID}.category")
            ClientRegistry.registerKeyBinding(setHorse)
            ClientRegistry.registerKeyBinding(callHorse)
            if (HorseConfig.enableStatsViewer?.get() == true) ClientRegistry.registerKeyBinding(showStats)
        }
    }
}