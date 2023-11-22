package com.example.prototype.data


@Suppress("EqualsAndHashCode")
data class KeyboardData(
    val isRandomized: Boolean = false,
    val layout: Array<Array<String>> = emptyArray(),
    val missClicks: List<Pair<Float, Float>> = emptyList(),
   // val buttonSize: Dp = 85.dp
)