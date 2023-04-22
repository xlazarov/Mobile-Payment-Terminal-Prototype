package com.example.prototype.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.prototype.R
import com.example.prototype.root.*
import com.example.prototype.data.PaymentState
import com.example.prototype.ui.theme.BlueButton
import com.example.prototype.ui.theme.Red


@Composable
fun FailedScreen(
    state: PaymentState,
    onRetryButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit
) {
    Box(modifier = Modifier.padding(top = 80.dp)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp)
                .gradient()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .screenPadding(),
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

@Composable
fun ErrorIcon() {
    Image(
        painter = painterResource(id = R.drawable.close),
        contentDescription = null,
        modifier = Modifier.size(100.dp)
    )
}



