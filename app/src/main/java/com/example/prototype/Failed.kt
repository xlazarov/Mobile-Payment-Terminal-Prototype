package com.example.prototype

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prototype.ui.theme.BlueButton
import com.example.prototype.ui.theme.Red


@Composable
fun FailedScreen(
    state: PaymentState,
    onRetryButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = yPad.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 57.dp)
                .gradientBackground()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = yPad.dp, start = xPad.dp, end = xPad.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ErrorIcon()
            TransactionResult(
                "Platba zlyhala",
                "Počet pokusov o zadanie kódu PIN bol prekročený."
            )
            Column {
                TransactionDetails(state)
                Spacer(modifier = Modifier.height(30.dp))
                NavigationButton(BlueButton, "Skúsiť znova", onRetryButtonClicked)
                Spacer(modifier = Modifier.height(12.dp))
                NavigationButton(Red, "Zrušiť", onCancelButtonClicked)
            }
        }
    }
}



