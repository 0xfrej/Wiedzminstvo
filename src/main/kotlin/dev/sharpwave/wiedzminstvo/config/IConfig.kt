package dev.sharpwave.wiedzminstvo.config

import net.minecraftforge.common.ForgeConfigSpec

interface IConfig {
    operator fun invoke(server: ForgeConfigSpec.Builder, client: ForgeConfigSpec.Builder)
}