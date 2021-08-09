package dev.sharpwave.wiedzminstvo.init

import com.electronwill.nightconfig.core.file.CommentedFileConfig
import com.electronwill.nightconfig.core.io.WritingMode
import dev.sharpwave.wiedzminstvo.Logger
import dev.sharpwave.wiedzminstvo.config.HorseConfig
import dev.sharpwave.wiedzminstvo.config.MainConfig
import net.minecraftforge.common.ForgeConfigSpec
import java.io.File

object ModConfig {
    private val serverBuilder = ForgeConfigSpec.Builder()
    private val clientBuilder = ForgeConfigSpec.Builder()
    val serverConfig: ForgeConfigSpec
    val clientConfig: ForgeConfigSpec

    init {
        // Build configs from each config definition class
        MainConfig(this.serverBuilder, this.clientBuilder)
        HorseConfig(this.serverBuilder, this.clientBuilder)

        // Build configs
        serverConfig = serverBuilder.build()
        clientConfig = clientBuilder.build()
    }

    fun loadConfig(configSpec: ForgeConfigSpec, path: String) {
        Logger.info("Loading config: $path")
        val file = CommentedFileConfig.builder(File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build()
        Logger.info("Built config: $path")
        file.load()
        Logger.info("Loaded config: $path")
        configSpec.setConfig(file)
    }
}