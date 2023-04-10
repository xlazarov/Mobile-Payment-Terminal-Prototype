package com.example.prototype

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prototype.ui.theme.BlueButton
import com.example.prototype.ui.theme.backgroundImage


@Composable
fun SuccessScreen(
    state: PaymentState,
    onCloseButtonClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 57.dp)
                .then(backgroundImage)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 35.dp, start = 22.dp, end = 22.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            SuccessIcon()
            TransactionResult(result = "Platba prebehla úspešne!", details = "Ďakujeme.")
            Column {
                TransactionDetails(state)
                Spacer(modifier = Modifier.height(30.dp))
                NavigationButton(BlueButton, "Zatvoriť", onCloseButtonClicked)
            }
        }
    }
}

