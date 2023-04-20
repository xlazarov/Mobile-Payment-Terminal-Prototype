package com.example.prototype.keyboard

import android.content.Context
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import com.example.prototype.app.gradient
import com.example.prototype.app.screenPadding


fun generateNumpad(isRandomized: Boolean): List<String> {
    val numpad = (0..9).map { it.toString() }
    return if (isRandomized) numpad.shuffled() else numpad
}

fun generateLayout(forPinScreen: Boolean, isRandomized: Boolean): Array<Array<String>> {
    val numpad = generateNumpad(isRandomized)

    return arrayOf(
        arrayOf(numpad[0], numpad[1], numpad[2]),
        arrayOf(numpad[3], numpad[4], numpad[5]),
        arrayOf(numpad[6], numpad[7], numpad[8]),
        if (forPinScreen) arrayOf(numpad[9]) else arrayOf(".", numpad[9], "Delete")
    )
}

fun handleButtonClick(
    symbol: String,
    onAction: (KeyboardAction, Context) -> Unit,
    context: Context,
    forPinScreen: Boolean
) {
    when (symbol) {
        "." -> onAction(KeyboardAction.Decimal, context)
        "Delete" -> onAction(KeyboardAction.Delete(false), context)
        else -> onAction(
            KeyboardAction.Number(symbol.toInt(), forPinScreen), context
        )
    }
}

@Composable
fun KeyboardLayout(
    onAction: (KeyboardAction, Context) -> Unit,
    context: Context,
    forPinScreen: Boolean,
    isRandomized: Boolean
) {
    val layout = generateLayout(forPinScreen, isRandomized)
    onAction(KeyboardAction.SetKeyboard(isRandomized = isRandomized, layout = layout), context)

    layout.forEach { row ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (row.size == 1) {
                Arrangement.SpaceEvenly
            } else {
                Arrangement.SpaceBetween
            }
        ) {
            row.forEach { symbol ->
                KeyboardButton(
                    symbol = symbol,
                    onClick = {
                        handleButtonClick(symbol, onAction, context, forPinScreen)
                    }
                )
            }
        }
    }
}

@Composable
fun Keyboard(
    onAction: (KeyboardAction, Context) -> Unit,
    forPinScreen: Boolean,
    isRandomized: Boolean
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier.gradient()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .screenPadding()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            onAction(KeyboardAction.MissClick(it.x, it.y), context)
                        }
                    )
                },
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            KeyboardLayout(
                onAction = onAction,
                context = context,
                forPinScreen = forPinScreen,
                isRandomized = isRandomized
            )
        }
    }
}



