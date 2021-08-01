package dev.sharpwave.wiedzminstvo.config

import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue


object MainConfig : IConfig {
    var isDebugEnabled: ForgeConfigSpec.BooleanValue? = null

    override operator fun invoke(server: ForgeConfigSpec.Builder, client: ForgeConfigSpec.Builder) {
        server.comment("Main configuration")
            isDebugEnabled = server
                .comment("Enable mod debugging")
                .define("debug", false)
    }
}