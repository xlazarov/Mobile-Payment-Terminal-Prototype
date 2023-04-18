package com.example.prototype

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun SimplifiedKeyboard(
    onAction: (KeyboardAction, Context) -> Unit,
    pinScreen: Boolean
) {
    val context = LocalContext.current
    val keyboardLayout: Array<Array<String>> = arrayOf(
        arrayOf("1", "2", "3"),
        arrayOf("4", "5", "6"),
        arrayOf("7", "8", "9"),
        if (pinScreen) arrayOf("0") else arrayOf(".", "0", "Delete")
    )

    onAction(
        KeyboardAction.SetKeyboard(
            isRandomized = false,
            layout = keyboardLayout,
            width = getScreenWidthPx()
        ), context
    )

    Box(
        modifier = Modifier.gradientBackground()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(vertical = yPad.dp, horizontal = xPad.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            onAction(KeyboardAction.MissClick(it.x, it.y), context)
                        }
                    )
                },
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            keyboardLayout.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Yellow),
                    horizontalArrangement = if (row.size == 1 && pinScreen) {
                        Arrangement.SpaceEvenly
                    } else {
                        Arrangement.SpaceBetween
                    }
                ) {
                    row.forEach { symbol ->
                        KeyboardButton(
                            symbol = symbol,
                            onClick = {
                                when (symbol) {
                                    "." -> onAction(KeyboardAction.Decimal, context)
                                    "Delete" -> onAction(KeyboardAction.Delete(false), context)
                                    else -> onAction(
                                        KeyboardAction.Number(symbol.toInt(), pinScreen),
                                        context
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

