package dev.sharpwave.wiedzminstvo.init

import com.electronwill.nightconfig.core.file.CommentedFileConfig
import com.electronwill.nightconfig.core.io.WritingMode
import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.config.HorseConfig
import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.fml.common.Mod
import java.io.File

@Mod.EventBusSubscriber
object ModConfig {
    private val serverBuilder = ForgeConfigSpec.Builder()
    private val clientBuilder = ForgeConfigSpec.Builder()
    val serverConfig: ForgeConfigSpec
    val clientConfig: ForgeConfigSpec

    init {
        // Build configs from each config definition class
        HorseConfig(this.serverBuilder, this.clientBuilder)

        // Build configs
        serverConfig = serverBuilder.build()
        clientConfig = clientBuilder.build()
    }

    fun loadConfig(configSpec: ForgeConfigSpec, path: String) {
        WiedzminstvoMod.logger.info("Loading config: $path")
        val file = CommentedFileConfig.builder(File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build()
        WiedzminstvoMod.logger.info("Built config: $path")
        file.load()
        WiedzminstvoMod.logger.info("Loaded config: $path")
        configSpec.setConfig(file)
    }
}