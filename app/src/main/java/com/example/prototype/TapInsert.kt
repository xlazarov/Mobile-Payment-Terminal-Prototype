package com.example.prototype

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
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
        modifier = Modifier
            .size(getScreenWidth().dp)
    )
}

@Composable
fun getScreenWidth(): Int {
    val width = with(LocalContext.current) {
        (resources.displayMetrics.widthPixels / resources.displayMetrics.density).toInt()
    }
    return width - 2 * xPad
}

@Composable
fun LEDprogressBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        val coroutineScope = rememberCoroutineScope()
        val continueReading = remember { mutableStateOf(true) }
        val ledWidth = (getScreenWidth().dp - (75.dp)) / 4
        val progress = remember { mutableStateOf(1) }
        val context = LocalContext.current

        NfcReader(onTagDiscovered = {
            if (continueReading.value) {
                continueReading.value = false
                vibration(context)
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

        for (i in 0 until progress.value) {
            Box(
                modifier = Modifier
                    .height(15.dp)
                    .width(ledWidth)
                    .clip(CircleShape)
                    .background(Green)
            )
        }
        for (i in progress.value until 4) {
            Box(
                modifier = Modifier
                    .height(15.dp)
                    .width(ledWidth)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Green.copy(alpha = 0.2f))
            )
        }
    }
}
