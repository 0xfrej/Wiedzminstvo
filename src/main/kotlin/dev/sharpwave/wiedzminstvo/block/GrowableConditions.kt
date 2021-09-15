package dev.sharpwave.wiedzminstvo.block

object GrowableConditions {
    // TODO: add real conditions for flowers and assign them
    val NONE = Condition(0,0)

    data class Condition (
        val minSurvivalBrightness: Int,
        val minGrowingBrightness: Int
    )
}