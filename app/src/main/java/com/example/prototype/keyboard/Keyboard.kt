package com.example.prototype.keyboard

import android.content.Context
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import com.example.prototype.root.verticalScreenPadding


/**
 * Generates the keypad order for the keyboard based on [isRandomized] boolean value.
 */
fun generateNumpad(isRandomized: Boolean): List<String> {
    val numpad = ((1..9) + 0).map { it.toString() }
    return if (isRandomized) numpad.shuffled() else numpad
}


/**
 * Returns the layout for the keyboard based on the specified configuration
 * of [forPinScreen] and [isRandomized].
 */
fun generateLayout(forPinScreen: Boolean, isRandomized: Boolean): Array<Array<String>> {
    val numpad = generateNumpad(isRandomized)

    return arrayOf(
        arrayOf(numpad[0], numpad[1], numpad[2]),
        arrayOf(numpad[3], numpad[4], numpad[5]),
        arrayOf(numpad[6], numpad[7], numpad[8]),
        if (forPinScreen) arrayOf(numpad[9]) else arrayOf(".", numpad[9], "Delete")
    )
}


/**
 * Handles the click event of a keyboard button.
 * Based on [symbol] associated with the clicked button, a specific [KeyboardAction] is called.
 */
fun handleButtonClick(
    symbol: String,
    onAction: (KeyboardAction, Context) -> Unit,
    context: Context,
    forPinScreen: Boolean
) {
    when (symbol) {
        "." -> onAction(KeyboardAction.Decimal, context)
        "Delete" -> onAction(KeyboardAction.Delete, context)
        else -> onAction(
            KeyboardAction.Number(symbol.toInt(), forPinScreen), context
        )
    }
}


/**
 * A composable function that represents a keyboard layout of given [context]
 * based on [forPinScreen] and [isRandomized] specification.
 * [onAction] is a callback function to be invoked when a keyboard action is performed.
 */
@Composable
fun KeyboardLayout(
    onAction: (KeyboardAction, Context) -> Unit,
    context: Context,
    forPinScreen: Boolean,
    isRandomized: Boolean
) {
    val layout = remember { generateLayout(forPinScreen, isRandomized) }
    onAction(KeyboardAction.Layout(isRandomized = isRandomized, layout = layout), context)

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


/**
 * Based on [forPinScreen] and [isRandomized] defines a keyboard
 * of given type of layout for a specific screen.
 * [onAction] is a callback function to be invoked when a keyboard action is performed.
 */
@Composable
fun Keyboard(
    onAction: (KeyboardAction, Context) -> Unit,
    forPinScreen: Boolean,
    isRandomized: Boolean
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScreenPadding()
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



