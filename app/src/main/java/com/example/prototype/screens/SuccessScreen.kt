package com.example.prototype.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.prototype.R
import com.example.prototype.data.PaymentState
import com.example.prototype.root.NavigationButton
import com.example.prototype.root.PlayTone
import com.example.prototype.root.TransactionDetails
import com.example.prototype.ui.theme.BlueButton
import com.example.prototype.ui.theme.Green
import com.example.prototype.ui.theme.Red


@Composable
fun SuccessScreen(
    state: PaymentState,
    onCloseButtonClicked: () -> Unit
) {
    PlayTone(R.raw.zapsplat_success_tone2)

    ResultScreenLayout(upperSpace = 80) {
        SuccessIcon()
        GenerateText("Platba prebehla\núspešne!", "Ďakujeme.")
        Column {
            TransactionDetails(state)
            Spacer(modifier = Modifier.height(30.dp))
            NavigationButton(BlueButton, "Zatvoriť", onCloseButtonClicked)
        }
    }
}


@Composable
fun IconWithBackground(icon: ImageVector, backgroundColor: Color) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .clip(CircleShape)
                .fillMaxSize()
                .background(backgroundColor)
                .padding(20.dp)
        ) {
            GetIcon(icon)
        }
    }
}

@Composable
fun SuccessIcon() {
    IconWithBackground(Icons.Rounded.Done, Green)
}

@Composable
fun FailureIcon() {
    IconWithBackground(Icons.Rounded.Close, Red)
}
