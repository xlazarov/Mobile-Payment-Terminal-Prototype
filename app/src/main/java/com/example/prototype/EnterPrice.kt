package com.example.prototype

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prototype.ui.theme.BabyBlue
import com.example.prototype.ui.theme.BlueButton
import com.example.prototype.ui.theme.LightBlue
import com.example.prototype.ui.theme.Red


@Composable
fun EnterPriceScreen(
    state: PaymentState,
    onAction: (KeyboardAction, Context) -> Unit,
    onCancelButtonClicked: () -> Unit = {},
    onContinueButtonClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = yPad.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Platba",
            color = LightBlue,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
        )
        KeyboardTextField(state = state)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom
        ) {
            SimplifiedKeyboard(
                onAction = onAction,
                pinScreen = false
            )
            Column(modifier = Modifier.padding(horizontal = xPad.dp)) {
                NavigationButton(BlueButton, "Pokračovať", onContinueButtonClicked)
                Spacer(modifier = Modifier.height(12.dp))
                NavigationButton(Red, "Zrušiť", onCancelButtonClicked)
            }
        }
    }
}

@Composable
fun KeyboardTextField(state: PaymentState) {
    Column(modifier = Modifier.padding(horizontal = xPad.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = state.price,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .weight(1f),
                maxLines = 1
            )
            Text(
                text = "EUR",
                textAlign = TextAlign.End,
                color = LightBlue,
                fontWeight = FontWeight.SemiBold
            )
        }
        TabRowDefaults.Divider(
            thickness = 1.dp,
            color = BabyBlue
        )
    }
}
