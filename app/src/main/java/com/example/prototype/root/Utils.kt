package com.example.prototype.root

import android.content.Context
import android.content.res.Resources
import android.media.AudioManager
import android.media.ToneGenerator
import android.nfc.NfcAdapter
import android.os.*
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
import kotlinx.coroutines.coroutineScope


var screenWidth = 0f

var buttonSize = 0.dp
var isRandomized = false

val LocalNfcAdapter = compositionLocalOf<NfcAdapter?> { null }

const val horizontalScreenPadding = 20
const val verticalScreenPadding = 20

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


/**
 * Returns a modifier of gradient background effect and custom shape.
 */
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


/**
 * Adds a bounce effect to the click behavior of a modifier and returns it.
 */
fun Modifier.bounceClick(onClick: () -> Unit) = composed {
    var pressed by remember { mutableStateOf(false) }
    val size by animateFloatAsState(if (pressed) 0.60f else 1f)

    this
        .graphicsLayer {
            scaleX = size
            scaleY = size
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { onClick() }
        )
        .pointerInput(pressed) {
            coroutineScope {
                awaitPointerEventScope {
                    pressed = if (pressed) {
                        waitForUpOrCancellation()
                        false
                    } else {
                        awaitFirstDown(false)
                        true
                    }
                }
            }
        }
}

@Composable
fun screenWidthNoMargin(): Dp =
    (LocalConfiguration.current.screenWidthDp).dp

@Composable
fun screenWidthDp(): Dp =
    (LocalConfiguration.current.screenWidthDp - 2 * horizontalScreenPadding).dp

@Composable
fun screenWidthPx(): Float = dpToPx(dp = screenWidthDp())

fun dpToPx(dp: Dp): Float {
    return dp.value * Resources.getSystem().displayMetrics.density
}


/**
 * Triggers a vibration of specified [duration] using the device's vibrator.
 */
fun vibrate(context: Context, duration: Long = 50) {
    val vibrator = ContextCompat.getSystemService(context, Vibrator::class.java)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val effect = VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator?.vibrate(effect)
    }
}

val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME)
fun playTone(){
    toneGenerator.startTone(ToneGenerator.TONE_DTMF_5, 100)
    toneGenerator.stopTone()
}

fun clickResponse(context: Context) {
    vibrate(context)
    playTone()
}