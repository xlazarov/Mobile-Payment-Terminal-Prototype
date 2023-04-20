package com.example.prototype.app

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.prototype.ui.theme.GreyBackground


var screenWidth = 0f
const val horizontalScreenPadding = 20
const val verticalScreenPadding = 30
fun Modifier.screenPadding(): Modifier = padding(
    horizontalScreenPadding.dp,
    verticalScreenPadding.dp
)

fun Modifier.gradient(): Modifier =
    clip(
        shape = RoundedCornerShape(50.dp).copy(
            bottomEnd = ZeroCornerSize,
            bottomStart = ZeroCornerSize
        )
    ).background(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.White,
                GreyBackground
            )
        )
    )

fun dpToPx(dp: Dp): Float {
    return dp.value * Resources.getSystem().displayMetrics.density
}

@Composable
fun getScreenWidthDp(): Dp =
    (LocalConfiguration.current.screenWidthDp - 2 * horizontalScreenPadding).dp

@Composable
fun getScreenWidthPx(): Float = dpToPx(dp = getScreenWidthDp())

fun vibration(context: Context) {
    val vibrator = ContextCompat.getSystemService(context, Vibrator::class.java)
    if (vibrator != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val effect = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator.vibrate(effect)
    }
}