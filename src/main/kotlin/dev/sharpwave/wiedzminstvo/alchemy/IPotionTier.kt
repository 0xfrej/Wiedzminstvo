package dev.sharpwave.wiedzminstvo.alchemy

interface IPotionTier {
    enum class AmplifierType {
        NONE,
        DURATION,
        STRENGTH
    }

    val uses: Int
    val amplifier: AmplifierType
    val amplifyBy: Double
}