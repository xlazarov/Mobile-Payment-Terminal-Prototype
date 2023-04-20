package com.example.prototype.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.example.prototype.NfcReader
import com.example.prototype.R
import com.example.prototype.app.*
import com.example.prototype.data.PaymentState
import com.example.prototype.ui.theme.Green
import com.example.prototype.ui.theme.Red
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
        Column { PaymentInfo(state = state) }
        PaymentAnimation()
        Text(
            text = "Priložte alebo vložte \nplatobnú kartu",
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        LedProgressBar(navController)
        NavigationButton(color = Red, text = "Zrušiť platbu", onCancelButtonClicked)
    }
}

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
        modifier = Modifier.size(getScreenWidthDp())
    )
}

@Composable
fun LedProgressBar(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val coroutineScope = rememberCoroutineScope()
        val progress = remember { mutableStateOf(1) }
        var tagDetected by remember { mutableStateOf(false) }

        if (!tagDetected) {
            NfcReader(onTagDiscovered = {
                coroutineScope.launch {
                    tagDetected = true // set tagDetected to true to stop further tag reading
                    for (i in 1..3) {
                        delay(250)
                        progress.value++
                    }
                    delay(250)
                    navController.navigate(PaymentScreen.PIN.name)
                }
            })
        }
        val numLeds = 4
        val spaceBetweenLeds = 20.dp
        val ledWidth = (getScreenWidthDp() - (spaceBetweenLeds * (numLeds - 1))) / numLeds

        for (i in 0 until numLeds) {
            val backgroundColor = if (i < progress.value) Green else Green.copy(alpha = 0.2f)
            Box(
                modifier = Modifier
                    .height(15.dp)
                    .width(ledWidth)
                    .clip(CircleShape)
                    .background(backgroundColor)
            )
        }
    }
}