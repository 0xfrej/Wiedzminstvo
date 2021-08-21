package dev.sharpwave.wiedzminstvo.alchemy

interface IPotionTier {
    enum class AmplifierType {
        DURATION,
        STRENGTH
    }

    val name: String
    val uses: Int
    val amplifier: AmplifierType
    val amplifyBy: Double
}