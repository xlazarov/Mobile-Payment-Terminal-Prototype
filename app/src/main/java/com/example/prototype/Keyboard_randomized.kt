package com.example.prototype

import android.content.Context
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.prototype.ui.theme.gradientBackground

@Composable
fun RandomizedKeyboard(
    onAction: (KeyboardAction, Context) -> Unit
) {
    val context = LocalContext.current
    val num = remember { (0..9).map { it.toString() }.shuffled() }
    val keyboardLayout: Array<Array<String>> = arrayOf(
        arrayOf(num[0], num[1], num[2]),
        arrayOf(num[3], num[4], num[5]),
        arrayOf(num[6], num[7], num[8]),
        arrayOf(num[9])
    )
    onAction(KeyboardAction.Layout(layout = keyboardLayout, isRandomized = true), context)

    Box(modifier = Modifier
        .gradientBackground()
        .pointerInput(Unit) {
            detectTapGestures(
                onTap = {
                    onAction(KeyboardAction.MissClick(it.x, it.y), context)
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
                        Arrangement.SpaceBetween
                    } else {
                        Arrangement.SpaceBetween
                    }
                ) {
                    row.forEach { symbol ->
                        KeyboardButton(
                            symbol = symbol,
                            onClick = {
                                onAction(
                                    KeyboardAction.Number(symbol.toInt(), true),
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

