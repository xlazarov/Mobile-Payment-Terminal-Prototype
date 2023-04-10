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
fun Keyboard(
    modifier: Modifier,
    onAction: (ButtonAction, Context) -> Unit,
    pinScreen: Boolean
) {
    val context = LocalContext.current

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
                .then(modifier),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                KeyboardButton(
                    symbol = "1",
                    onClick = {
                        onAction(
                            ButtonAction.Number(1, pinScreen), context
                        )
                    },
                    pinScreen = pinScreen
                )
                KeyboardButton(
                    symbol = "2",
                    onClick = {
                        onAction(
                            ButtonAction.Number(2, pinScreen), context
                        )
                    },
                    pinScreen = pinScreen
                )
                KeyboardButton(
                    symbol = "3",
                    onClick = {
                        onAction(
                            ButtonAction.Number(3, pinScreen), context
                        )
                    },
                    pinScreen = pinScreen
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                KeyboardButton(
                    symbol = "4",
                    onClick = {
                        onAction(
                            ButtonAction.Number(4, pinScreen), context
                        )
                    },
                    pinScreen = pinScreen
                )
                KeyboardButton(
                    symbol = "5",
                    onClick = {
                        onAction(
                            ButtonAction.Number(5, pinScreen), context
                        )
                    },
                    pinScreen = pinScreen
                )
                KeyboardButton(
                    symbol = "6",
                    onClick = {
                        onAction(
                            ButtonAction.Number(6, pinScreen), context
                        )
                    },
                    pinScreen = pinScreen
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                KeyboardButton(
                    symbol = "7",
                    onClick = {
                        onAction(
                            ButtonAction.Number(7, pinScreen), context
                        )
                    },
                    pinScreen = pinScreen
                )
                KeyboardButton(
                    symbol = "8",
                    onClick = {
                        onAction(
                            ButtonAction.Number(8, pinScreen), context
                        )
                    },
                    pinScreen = pinScreen
                )
                KeyboardButton(
                    symbol = "9",
                    onClick = {
                        onAction(
                            ButtonAction.Number(9, pinScreen), context
                        )
                    },
                    pinScreen = pinScreen
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (pinScreen) Arrangement.Center else Arrangement.spacedBy(10.dp)

            ) {
                if (!pinScreen) {
                    KeyboardButton(
                        symbol = ".",
                        onClick = {
                            onAction(
                                ButtonAction.Decimal, context
                            )
                        },
                        pinScreen = false
                    )
                }
                KeyboardButton(
                    symbol = "0",
                    onClick = {
                        onAction(
                            ButtonAction.Number(0, pinScreen), context
                        )
                    },
                    pinScreen = pinScreen
                )
                if (!pinScreen) {
                    KeyboardButton(
                        symbol = "Delete",
                        onClick = {
                            onAction(
                                ButtonAction.Delete(false), context
                            )
                        },
                        pinScreen = false
                    )
                }
            }
        }
    }
}

