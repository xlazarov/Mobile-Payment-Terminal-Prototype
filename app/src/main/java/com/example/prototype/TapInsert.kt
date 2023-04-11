package com.example.prototype

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.example.prototype.ui.theme.Green
import com.example.prototype.ui.theme.Red
import com.example.prototype.ui.theme.xPad
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
            .padding(vertical = 35.dp, horizontal = 22.dp)
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
        LEDprogressBar(navController)
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
        modifier = Modifier.size(getScreenWidth())
    )
}

@Composable
fun getScreenWidth(): Dp {
    return (LocalConfiguration.current.screenWidthDp - 2 * xPad).dp
}

@Composable
fun LEDprogressBar(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val coroutineScope = rememberCoroutineScope()
        val progress = remember { mutableStateOf(1) }
        val lastTag = remember { mutableStateOf<String?>(null) }

        NfcReader(onTagDiscovered = { tag: String? ->
            if (lastTag.value != tag) {
                lastTag.value = tag
                coroutineScope.launch {
                    for (i in 1..3) {
                        delay(250)
                        progress.value++
                    }
                    delay(250)
                    navController.navigate(PaymentScreen.PIN.name)
                }
            }
        })
        val numLeds = 4
        val spaceBetweenLeds = 20.dp
        val ledWidth = (getScreenWidth() - (spaceBetweenLeds * (numLeds - 1))) / numLeds

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