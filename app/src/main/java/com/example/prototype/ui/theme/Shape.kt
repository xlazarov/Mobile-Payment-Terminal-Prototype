package com.example.prototype.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

val CancelBubble = RoundedCornerShape(30.dp).copy(bottomEnd = ZeroCornerSize)
val ConfirmBubble = RoundedCornerShape(30.dp).copy(bottomStart = ZeroCornerSize)
