package com.example.prototype


@Suppress("EqualsAndHashCode")
data class KeyboardData(
    val isRandomized: Boolean = false,
    val keyboardLayout: Array<Array<String>> = emptyArray(),
    val missClickData: List<Pair<Float, Float>> = emptyList()
)