package com.example.prototype.root

import android.content.Context
import android.content.res.Resources
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.prototype.ui.theme.GreyBackground


var screenWidth = 0f

const val horizontalScreenPadding = 20
const val verticalScreenPadding = 30

fun Modifier.horizontalScreenPadding(): Modifier = padding(
    horizontal = horizontalScreenPadding.dp
)

fun Modifier.verticalScreenPadding(): Modifier = padding(
    vertical = verticalScreenPadding.dp
)

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

fun Modifier.bounceClick() = composed {
    var buttonPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (buttonPressed) 0.60f else 1f)

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { }
        )
        .pointerInput(buttonPressed) {
            awaitPointerEventScope {
                buttonPressed = if (buttonPressed) {
                    waitForUpOrCancellation()
                    false
                } else {
                    awaitFirstDown(false)
                    true
                }
            }
        }
}

@Composable
fun getScreenWidthDp(): Dp =
    (LocalConfiguration.current.screenWidthDp - 2 * horizontalScreenPadding).dp

@Composable
fun getScreenWidthPx(): Float = dpToPx(dp = getScreenWidthDp())

fun dpToPx(dp: Dp): Float {
    return dp.value * Resources.getSystem().displayMetrics.density
}

fun vibrate(context: Context, duration: Long = 50) {
    val vibrator = ContextCompat.getSystemService(context, Vibrator::class.java)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val effect = VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator?.vibrate(effect)
    }
}

fun clickResponse(context: Context) {
    vibrate(context)
    val toneGenerator = ToneGenerator(AudioManager.STREAM_DTMF, 80)
    toneGenerator.startTone(ToneGenerator.TONE_DTMF_5, 50)
}