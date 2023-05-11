package com.example.prototype.data

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Suppress("EqualsAndHashCode")
data class KeyboardData(
    val isRandomized: Boolean = false,
    val layout: Array<Array<String>> = emptyArray(),
    val missClicks: List<Pair<Float, Float>> = emptyList(),
    val buttonSize: Dp = 70.dp
)