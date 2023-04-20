package com.example.prototype.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prototype.app.gradient
import com.example.prototype.app.screenPadding
import com.example.prototype.data.PaymentState
import com.example.prototype.ui.theme.BlueButton


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
                .gradient()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .screenPadding()
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

