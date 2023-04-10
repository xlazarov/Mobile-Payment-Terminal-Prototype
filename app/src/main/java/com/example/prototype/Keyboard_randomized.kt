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
import com.example.prototype.ui.theme.gradientBackground

@Composable
fun RandomizedKeyboard(
    onAction: (ButtonAction, Context) -> Unit
) {
    val context = LocalContext.current
    val num = (0..9).shuffled()
    val keyboardLayout = listOf(
        listOf(num[0], num[1], num[2]),
        listOf(num[3], num[4], num[5]),
        listOf(num[6], num[7], num[8]),
        listOf(num[9])
    )

    Box(modifier = Modifier
        .gradientBackground()
        .pointerInput(Unit) {
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
                    horizontalArrangement = if (row.size > 1) {
                        Arrangement.spacedBy(10.dp)
                    } else {
                        Arrangement.SpaceBetween
                    }
                ) {
                    row.forEach { symbol ->
                        KeyboardButton(
                            symbol = symbol.toString(),
                            onClick = {
                                onAction(
                                    ButtonAction.Number(symbol, true),
                                    context
                                )
                            },
                            pinScreen = true
                        )
                    }
                }
            }
        }
    }
}

