package dev.sharpwave.wiedzminstvo.alchemy

interface IPotionTier {
    enum class AmplifierType {
        NONE,
        DURATION,
        STRENGTH
    }

    val name: String
    val uses: Int
    val amplifier: AmplifierType
    val amplifyBy: Double
    val color: Int

    fun amplifyDuration(duration: Int): Int
    fun amplifyStrength(strength: Int): Int
}