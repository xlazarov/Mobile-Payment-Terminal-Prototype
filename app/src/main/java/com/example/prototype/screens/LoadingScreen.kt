package com.example.prototype.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prototype.ui.theme.LightBlue
import kotlinx.coroutines.delay
import kotlin.random.Random

@ExperimentalAnimationApi
@Composable
fun LoadingScreen() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(175.dp))
        LoadingAnimation()
        Spacer(modifier = Modifier.height(35.dp))
        PaymentProgressText()
    }
}

@Composable
fun PaymentProgressText() {
    val count = remember { mutableStateOf(1) }
    val textAnimation = mapOf(1 to "Pripájam", 2 to "Spracúvam", 3 to "Prijímam", 4 to "Odpájam")

    LaunchedEffect(key1 = Unit) {
        for (i in 1..3) {
            delay(500 + Random.nextLong(0, 500))
            count.value++
        }
    }

    GenerateText(
        title = textAnimation[count.value].toString(),
        subtitle = "Prosím počkajte..."
    )
}

@Composable
fun LoadingAnimation() {
    var count by remember { mutableStateOf(-1) }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(250)
            count++
        }
    }

    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(40.dp),
        modifier = Modifier.height(50.dp)
    ) {
        GenerateLoadingBar(count = count)
    }
}

@Composable
fun GenerateLoadingBar(count: Int) {
    for (i in 0 until 4) {
        val offset by animateDpAsState(
            targetValue = if (i == count % 4) 35.dp else 0.dp, tween(250)
        )
        val opacity by animateFloatAsState(
            targetValue = if (i == count % 4) 0.3f else 1f, tween(250)
        )

        Box(
            modifier = Modifier.padding(bottom = offset),
            contentAlignment = Alignment.BottomCenter
        ) {
            Dot(color = LightBlue.copy(alpha = opacity))
        }
    }
}
