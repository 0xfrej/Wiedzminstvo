package dev.sharpwave.wiedzminstvo.alchemy

import dev.sharpwave.support.register.SimpleRegister

object PotionTiers : SimpleRegister<Int, IPotionTier>() {

    class PotionTier(override val name: String, override val uses: Int, override val amplifier: IPotionTier.AmplifierType, override val amplifyBy: Double) : IPotionTier

    val ENHANCED_DURATION by register(0, PotionTier("enhanced_duration",4, IPotionTier.AmplifierType.DURATION, 1.0))
    val ENHANCED_STRENGTH by register(1, PotionTier("enhanced_strength", 4, IPotionTier.AmplifierType.STRENGTH, 1.0))
    val MINOR_DURATION by register(2, PotionTier("minor_duration", 6, IPotionTier.AmplifierType.DURATION, 1.25))
    val MINOR_STRENGTH by register(3, PotionTier("minor_strength", 6, IPotionTier.AmplifierType.STRENGTH, 1.25))
    val MAJOR_DURATION by register(4, PotionTier("major_duration", 8, IPotionTier.AmplifierType.DURATION, 1.5))
    val MAJOR_STRENGTH by register(5, PotionTier("major_strength", 8, IPotionTier.AmplifierType.STRENGTH, 1.5))
    val MAGIC_DURATION by register(6, PotionTier("magic_duration", 12, IPotionTier.AmplifierType.DURATION, 2.0))
    val MAGIC_STRENGTH by register(7, PotionTier("magic_strength", 12, IPotionTier.AmplifierType.STRENGTH, 2.0))
    val CREATIVE_DURATION by register(8, PotionTier("creative_duration", -1, IPotionTier.AmplifierType.DURATION, 2.0))
    val CREATIVE_STRENGTH by register(9, PotionTier("creative_strength", -1, IPotionTier.AmplifierType.STRENGTH, 2.0))
}