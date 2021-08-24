package dev.sharpwave.wiedzminstvo.config

import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue


object HorseConfig : IConfig {
    var deathIsPermanent: ForgeConfigSpec.BooleanValue? = null
    var callableDimsWhitelist: ConfigValue<List<String>>? = null
    var maxCallingDistance: ForgeConfigSpec.IntValue? = null
    var enableStatsViewer: ForgeConfigSpec.BooleanValue? = null
    var walkRange: ForgeConfigSpec.DoubleValue? = null
    var walkSpeed: ForgeConfigSpec.DoubleValue? = null
    var continuousAntiDupeChecking: ForgeConfigSpec.BooleanValue? = null

    override operator fun invoke(server: ForgeConfigSpec.Builder, client: ForgeConfigSpec.Builder) {
        server.comment("Horse").push("horse")
        deathIsPermanent = server
            .comment("Personal Horses get deleted if they're killed")
            .define("deathIsPermanent", false)

        callableDimsWhitelist = server
            .comment("Whitelist for dimensions where horses can be called. Set to empty to allow calling the horse in every dimension")
            .defineList("callableDimsWhitelist", listOf("minecraft:overworld")) { obj -> obj is String }

        maxCallingDistance = server
            .comment("Maximum block distance from last horse where new horse can be called. Set to -1 to disable range.")
            .defineInRange("maxCallingDistance", -1, -1, 30000000)

        enableStatsViewer = server
            .comment("Enable/disable the horse stat viewer GUI")
            .define("enableStatsViewer", true)

        walkRange = server
            .comment("Range in which the horse will not teleport, but walk to you. Set to 0 to force the horse to always teleport.")
            .defineInRange("walkRange", 30.0, 0.0, 64.0)

        walkSpeed = server
            .comment("Speed with which the horse walks when you call it. Vanilla horse walk speed is 1.2")
            .defineInRange("walkSpeed", 1.8, 1.2, 3.0)

        // TODO: remove this with feature
        continuousAntiDupeChecking = server
            .comment("Check against duplicate horses every game tick (20 times per second) instead of only on load. Only use this if you're experiencing problem with duplicate horses! THIS COULD CAUSE LAG!")
            .define("continuousAntiDupeChecking", false)
        server.pop()
    }
}