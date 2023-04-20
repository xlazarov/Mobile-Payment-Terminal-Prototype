package com.example.prototype.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prototype.ui.theme.LightBlue
import kotlinx.coroutines.delay
import kotlin.random.Random

@ExperimentalAnimationApi
@Composable
fun LoadingScreen() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(175.dp))
        LoadingBar()
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
            delay(600 + Random.nextLong(0, 500))
            count.value++
        }
    }
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = textAnimation[count.value].toString(),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Prosím počkajte...",
            fontSize = 18.sp
        )
    }
}

@Composable
fun LoadingBar() {
    var count by remember { mutableStateOf(-1) }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(300)
            count++
        }
    }
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(40.dp),
        modifier = Modifier.height(50.dp)
    ) {
        for (i in 0 until 4) {
            val offset by animateDpAsState(
                targetValue = if (i == count % 4) 35.dp else 0.dp, tween(300)
            )
            val opacity by animateFloatAsState(
                targetValue = if (i == count % 4) 0.3f else 1f, tween(300)
            )
            Box(modifier = Modifier.padding(bottom = offset), contentAlignment = Alignment.BottomCenter) {
                Box(
                    modifier = Modifier
                        .size(15.dp)
                        .clip(CircleShape)
                        .background(LightBlue.copy(alpha = opacity))
                )
            }
        }
    }
}
