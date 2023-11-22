package com.example.prototype.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prototype.R
import com.example.prototype.data.PaymentState
import com.example.prototype.root.*
import com.example.prototype.ui.theme.BlueButton
import com.example.prototype.ui.theme.Green


/**
 * Composable function that represents a screen indicating payment success.
 *
 * Payment [state] contains relevant payment data to display,
 * and [onCloseButtonClicked] is function invoked when close button is clicked.
 */
@Composable
fun PaymentSuccessScreen(
    state: PaymentState,
    onCloseButtonClicked: () -> Unit
) {
    PlayTone(R.raw.zapsplat_success_tone2)

    ResultScreenLayout(upperSpace = 40) {
        SuccessIcon()
        GenerateText("Platba prebehla\núspešne!", "Ďakujeme.")
        Column {
            TransactionDetails(state)
            Spacer(modifier = Modifier.height(20.dp))
            NavigationButton(BlueButton, "Zatvoriť", onCloseButtonClicked)
        }
    }
}


@Composable
fun SuccessIcon() {
    IconWithBackground(Icons.Rounded.Done, Green)
}

