package dev.sharpwave.wiedzminstvo.alchemy

import dev.sharpwave.support.register.SimpleRegister

object PotionTiers : SimpleRegister<String, IPotionTier>() {

    class PotionTier(override val uses: Int, override val amplifier: IPotionTier.AmplifierType, override val amplifyBy: Double) : IPotionTier

    val ENHANCED_DURATION by register("enhanced_duration", PotionTier(4, IPotionTier.AmplifierType.DURATION, 1.0))
    val ENHANCED_STRENGTH by register("enhanced_strength", PotionTier(4, IPotionTier.AmplifierType.STRENGTH, 1.0))
    val MINOR_DURATION by register("minor_duration", PotionTier(6, IPotionTier.AmplifierType.DURATION, 1.25))
    val MINOR_STRENGTH by register("minor_strength", PotionTier(6, IPotionTier.AmplifierType.STRENGTH, 1.25))
    val MAJOR_DURATION by register("major_duration", PotionTier(8, IPotionTier.AmplifierType.DURATION, 1.5))
    val MAJOR_STRENGTH by register("major_strength", PotionTier(8, IPotionTier.AmplifierType.STRENGTH, 1.5))
    val MAGIC_DURATION by register("magic_duration", PotionTier(12, IPotionTier.AmplifierType.DURATION, 2.0))
    val MAGIC_STRENGTH by register("magic_strength", PotionTier(12, IPotionTier.AmplifierType.STRENGTH, 2.0))
}