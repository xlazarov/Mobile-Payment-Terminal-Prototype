package com.example.prototype.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prototype.data.PaymentState
import com.example.prototype.keyboard.Keyboard
import com.example.prototype.keyboard.KeyboardAction
import com.example.prototype.root.NavigationButton
import com.example.prototype.root.horizontalScreenPadding
import com.example.prototype.root.verticalScreenPadding
import com.example.prototype.ui.theme.*

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
            .verticalScreenPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Platba", style = Typography.h4, fontSize = 24.sp)
        KeyboardTextField(state = state)
        Column(
            verticalArrangement = Arrangement.Bottom
        ) {
            Keyboard(onAction = onAction, forPinScreen = false, isRandomized = false)
            GenerateButtons(state, onCancelButtonClicked, onContinueButtonClicked)
        }
    }
}

@Composable
fun GenerateButtons(
    state: PaymentState,
    onCancelButtonClicked: () -> Unit = {},
    onContinueButtonClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier.padding(horizontal = horizontalScreenPadding.dp)
    ) {
        NavigationButton(
            BlueButton,
            "Pokračovať",
            onContinueButtonClicked,
            !(state.price.startsWith("0") && state.price.toFloat() == 0f)
        )
        Spacer(modifier = Modifier.height(12.dp))
        NavigationButton(Red, "Zrušiť", onCancelButtonClicked)
    }
}

@Composable
fun KeyboardTextField(state: PaymentState) {
    Column(modifier = Modifier.padding(horizontal = horizontalScreenPadding.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = state.price,
                style = Typography.h1,
                modifier = Modifier.weight(1f),
                maxLines = 1
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "EUR",
                style = Typography.h1,
                color = LightBlue,
                fontWeight = FontWeight.Normal
            )
        }
        Divider(color = LightBlue)
    }
}
