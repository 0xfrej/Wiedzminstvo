package dev.sharpwave.wiedzminstvo.alchemy

// TODO: Add named tiers
class PotionTiers {
    private val entries = HashMap<>()

    class PotionTier(override val uses: Int, override val amplifier: IPotionTier.AmplifierType, override val amplifyBy: Double) : IPotionTier

    companion object {
        val BASE = PotionTier(2, IPotionTier.AmplifierType.NONE, 0.0)
        val ENHANCED_DURATION = PotionTier(4, IPotionTier.AmplifierType.DURATION, 1.0)
        val ENHANCED_STRENGHT = PotionTier(4, IPotionTier.AmplifierType.STRENGTH, 1.0)
        val MINOR_DURATION = PotionTier(6, IPotionTier.AmplifierType.DURATION, 1.25)
        val MINOR_STRENGHT = PotionTier(6, IPotionTier.AmplifierType.STRENGTH, 1.25)
        val MAJOR_DURATION = PotionTier(8, IPotionTier.AmplifierType.DURATION, 1.5)
        val MAJOR_STRENGHT = PotionTier(8, IPotionTier.AmplifierType.STRENGTH, 1.5)
        val MAGIC_DURATION = PotionTier(12, IPotionTier.AmplifierType.DURATION, 2.0)
        val MAGIC_STRENGHT = PotionTier(12, IPotionTier.AmplifierType.STRENGTH, 2.0)
    }

    private fun register() {

    }
}