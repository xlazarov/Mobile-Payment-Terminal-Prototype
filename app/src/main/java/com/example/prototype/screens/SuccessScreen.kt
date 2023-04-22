package com.example.prototype.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.prototype.R
import com.example.prototype.data.PaymentState
import com.example.prototype.root.*
import com.example.prototype.ui.theme.BlueButton


@Composable
fun SuccessScreen(
    state: PaymentState,
    onCloseButtonClicked: () -> Unit
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

@Composable
fun SuccessIcon() {
    val image = painterResource(R.drawable.icon_success)
    Image(
        painter = image,
        contentDescription = null,
        modifier = Modifier.size(100.dp)
    )
}