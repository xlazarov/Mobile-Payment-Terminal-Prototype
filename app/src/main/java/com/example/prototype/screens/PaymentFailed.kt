package com.example.prototype.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prototype.R
import com.example.prototype.data.PaymentState
import com.example.prototype.root.*
import com.example.prototype.ui.theme.BlueButton
import com.example.prototype.ui.theme.Red


/**
 * Composable function that represents a screen indicating payment failed.
 *
 * Payment [state] contains relevant payment data to display and [onRetryButtonClicked],
 * [onCancelButtonClicked] are functions invoked when selected button is clicked.
 */
@Composable
fun PaymentFailedScreen(
    state: PaymentState,
    onRetryButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit
) {
    PlayTone(R.raw.zapsplat_error_tone1)

    ResultScreenLayout(upperSpace = 20) {
        FailureIcon()
        GenerateText(
            "Platba zlyhala",
            "Počet pokusov o zadanie kódu PIN bol prekročený."
        )
        Column {
            TransactionDetails(state)
            Spacer(modifier = Modifier.height(20.dp))
            NavigationButton(BlueButton, "Skúsiť znova", onRetryButtonClicked)
            Spacer(modifier = Modifier.height(12.dp))
            NavigationButton(Red, "Zrušiť", onCancelButtonClicked)
        }
    }
}


@Composable
fun FailureIcon() {
    IconWithBackground(Icons.Rounded.Close, Red)
}