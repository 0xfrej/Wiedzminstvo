package dev.sharpwave.wiedzminstvo.alchemy

import dev.sharpwave.minecraft.register.SimpleRegister

object PotionTiers : SimpleRegister<Int, IPotionTier>() {

    class PotionTier(
        override val name: String,
        override val uses: Int,
        override val amplifier: IPotionTier.AmplifierType,
        override val amplifyBy: Double,
        override val color: Int
    ) : IPotionTier {
        override fun amplifyDuration(duration: Int): Int {
            return if (amplifier === IPotionTier.AmplifierType.DURATION) {
                return (duration * amplifyBy).toInt()
            }
            else {
                duration
            }
        }

        override fun amplifyStrength(strength: Int): Int {
            return if (amplifier === IPotionTier.AmplifierType.STRENGTH) {
                return (strength * amplifyBy).toInt()
            }
            else {
                strength
            }
        }
    }

    // TODO: add real colors
    val BASE by register(0, PotionTier("base", 1, IPotionTier.AmplifierType.NONE, 0.0, 0))
    val ENHANCED_DURATION by register(1, PotionTier("enhanced_duration", 4, IPotionTier.AmplifierType.DURATION, 1.0, 0))
    val ENHANCED_STRENGTH by register(2, PotionTier("enhanced_strength", 4, IPotionTier.AmplifierType.STRENGTH, 1.0, 0))
    val MINOR_DURATION by register(3, PotionTier("minor_duration", 6, IPotionTier.AmplifierType.DURATION, 1.25, 0))
    val MINOR_STRENGTH by register(4, PotionTier("minor_strength", 6, IPotionTier.AmplifierType.STRENGTH, 1.25, 0))
    val MAJOR_DURATION by register(5, PotionTier("major_duration", 8, IPotionTier.AmplifierType.DURATION, 1.5, 0))
    val MAJOR_STRENGTH by register(6, PotionTier("major_strength", 8, IPotionTier.AmplifierType.STRENGTH, 1.5, 0))
    val MAGIC_DURATION by register(7, PotionTier("magic_duration", 12, IPotionTier.AmplifierType.DURATION, 2.0, 0))
    val MAGIC_STRENGTH by register(8, PotionTier("magic_strength", 12, IPotionTier.AmplifierType.STRENGTH, 2.0, 0))
}