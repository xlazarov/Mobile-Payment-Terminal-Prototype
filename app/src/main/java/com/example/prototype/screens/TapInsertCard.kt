package com.example.prototype.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.example.prototype.NfcReader
import com.example.prototype.R
import com.example.prototype.data.PaymentState
import com.example.prototype.root.*
import com.example.prototype.ui.theme.Green
import com.example.prototype.ui.theme.Red
import com.example.prototype.ui.theme.Typography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Composable function that represents a screen inviting the customer to pay.
 *
 * Payment [state] contains relevant payment data to display,
 * [navController] is used for navigation and [onCancelButtonClicked] is a function
 * invoked when cancel button clicked.
 */
@Composable
fun TapCardScreen(
    state: PaymentState,
    navController: NavController,
    onCancelButtonClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .screenPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        PaymentInfo(state = state)
        PaymentAnimation()
        Text(
            text = "Priložte alebo vložte \nplatobnú kartu", style = Typography.h2
        )
        LedProgressBar(navController)
        NavigationButton(color = Red, text = "Zrušiť platbu", onCancelButtonClicked)
    }
}


/**
 *  Represents a payment animation using Lottie library.
 */
@Composable
fun PaymentAnimation() {
    val compositeResult: LottieCompositionResult = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            R.raw.cardanimation
        )
    )
    val progressAnimation by animateLottieCompositionAsState(
        compositeResult.value,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f
    )
    LottieAnimation(
        composition = compositeResult.value,
        progress = progressAnimation,
        modifier = Modifier.size(screenWidthDp())
    )
}


// LED PROGRESS BAR

/**
 * Displays an NFC progress bar with LED lights, animated when NFC tag is detected by [NfcReader].
 * After the animation is finished, function navigates to [PaymentScreen.PIN] by [navController].
 */
@Composable
fun LedProgressBar(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        val coroutineScope = rememberCoroutineScope()
        val progress = remember { mutableStateOf(1) }
        var tagDetected by remember { mutableStateOf(false) }

        if (!tagDetected) {
            NfcReader(onTagDiscovered = {
                coroutineScope.launch {
                    tagDetected = true // set tagDetected to true to stop further tag reading
                    for (i in 1..3) {
                        delay(100)
                        progress.value++
                    }
                    delay(750)
                    navController.navigate(PaymentScreen.PIN.name)
                }
            })
        }
        GenerateLedLights(progress = progress.value)
    }
}

@Composable
fun GenerateLedLights(
    numLeds: Int = 4,
    spacing: Dp = 20.dp,
    progress: Int
) {
    val ledWidth = (screenWidthDp() - (spacing * (numLeds - 1))) / numLeds

    for (i in 0 until numLeds) {
        val color = if (i < progress) Green else Green.copy(alpha = 0.2f)
        LedLight(width = ledWidth, color = color)
    }
}

@Composable
fun LedLight(width: Dp, color: Color) {
    Box(
        modifier = Modifier
            .height(15.dp)
            .width(width)
            .clip(CircleShape)
            .background(color)
    )
}