package com.example.prototype

import android.content.Context
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.prototype.ui.theme.backgroundImage

@Composable
fun SimplifiedKeyboard(
    onAction: (ButtonAction, Context) -> Unit,
    pinScreen: Boolean
) {
    val context = LocalContext.current
    val keyboardLayout = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf(".", "0", "Delete")
    )

    Box(modifier = backgroundImage.pointerInput(Unit) {
        detectTapGestures(
            onTap = {
                onAction(ButtonAction.MissClick(it.x, it.y), context)
            }
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(vertical = 30.dp, horizontal = 22.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            keyboardLayout.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (row.contains(".") && pinScreen) {
                        Arrangement.SpaceBetween
                    } else {
                        Arrangement.spacedBy(10.dp)
                    }
                ) {
                    row.forEach { symbol ->
                        KeyboardButton(
                            symbol = symbol,
                            onClick = {
                                when (symbol) {
                                    "." -> onAction(ButtonAction.Decimal, context)
                                    "Delete" -> onAction(ButtonAction.Delete(false), context)
                                    else -> onAction(
                                        ButtonAction.Number(symbol.toInt(), pinScreen),
                                        context
                                    )
                                }
                            },
                            pinScreen = pinScreen
                        )
                    }
                }
            }
        }
    }
}

