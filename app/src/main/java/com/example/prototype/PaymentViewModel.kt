package com.example.prototype

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PaymentViewModel : ViewModel() {

    var state by mutableStateOf(PaymentState())
    private var keyboard by mutableStateOf(KeyboardData())

    fun onAction(action: KeyboardAction, context: Context) {
        if ((action !is KeyboardAction.MissClick) && (action !is KeyboardAction.SetKeyboard)) {
            vibration(context)
        }
        when (action) {
            is KeyboardAction.Number -> enterNumber(action.number, action.pinScreen)
            is KeyboardAction.Delete -> delete(action.pinScreen)
            is KeyboardAction.Decimal -> enterDecimal()
            is KeyboardAction.MissClick -> recordCoordinates(action.x, action.y)
            is KeyboardAction.SetKeyboard -> setKeyboard(
                action.isRandomized,
                action.layout,
                action.width
            )
        }
    }

    fun alignDecimal() {
        if (!state.price.contains(".")) {
            state = state.copy(price = state.price + ".00")
            return
        }
        state = when (decimalLength()) {
            0 -> state.copy(price = state.price + "00")
            1 -> state.copy(price = state.price + "0")
            else -> return
        }
    }

    fun correctPin(): Boolean {
        return state.pin == CORRECT_PIN
    }

    fun wrongPin() {
        state = state.copy(pin = "", pinAttempts = state.pinAttempts - 1)
    }

    private fun resetPin() {
        state = state.copy(pin = "")
    }

    fun resetPinAttempts() {
        state = state.copy(pinAttempts = 3)
    }

    private fun resetTapData() {
        keyboard = keyboard.copy(missClicks = emptyList())
    }

    fun resetPayment() {
        resetPrice()
        resetPin()
        resetPinAttempts()
    }

    private fun resetPrice() {
        state = state.copy(price = "0")
    }

    private fun formatPriceString() {
        val price = state.price.reversed().replace(" ", "")
            .chunked(3).joinToString(separator = " ")

        state = state.copy(price = price.reversed())
    }

    private fun delete(pinScreen: Boolean) {
        // Delete from PIN
        if (pinScreen) {
            state = state.copy(pin = "")
            return
        }

        // Delete from price
        if (state.price != "0") {
            state = state.copy(price = state.price.dropLast(1))
            formatPriceString()
            if (state.price.isBlank()) {
                resetPrice()
            }
        }
    }

    private fun decimalLength(): Int {
        return state.price.substringAfter(".").length
    }

    private fun enterDecimal() {
        if (!state.price.contains(".")) {
            state = state.copy(
                price = state.price + "."
            )
        }
    }

    private fun enterDecimal(number: Int) {
        if (decimalLength() == MAX_DECIMAL_LENGTH) return
        state = state.copy(price = state.price + number)
    }

    private fun enterNumber(number: Int, pinScreen: Boolean) {
        if (pinScreen) {
            if (state.pin.length == MAX_PIN_LENGTH) return
            state = state.copy(pin = state.pin + number)
            return
        }

        if (state.price.contains(".")) {
            return enterDecimal(number)
        }
        if ((state.price.length >= MAX_NUM_LENGTH)) return

        state = if (state.price == "0") {
            state.copy(price = "" + number)
        } else {
            state.copy(price = state.price + number)
        }
        formatPriceString()
    }

    private fun setKeyboard(isRandomized: Boolean, layout: Array<Array<String>>, width: Float) {
        keyboard = keyboard.copy(
            isRandomized = isRandomized,
            layout = layout,
            width = width
        )
    }

    private fun recordCoordinates(x: Float, y: Float) {
        keyboard = keyboard.copy(missClicks = keyboard.missClicks + Pair(x, y))
    }

    private fun getRow(y: Float): Int {
        return when {
            y <= dpToPx(keyboard.buttonSize) -> 1
            y <= 2 * dpToPx(keyboard.buttonSize) -> 2
            y <= 3 * dpToPx(keyboard.buttonSize) -> 3
            else -> 4
        }
    }

    private fun getButtonMargin(): Float =
        (keyboard.width - 3 * dpToPx(keyboard.buttonSize)) / 4

    private fun getColumn(x: Float): Int {
        val margin = getButtonMargin()
        return when {
            x <= dpToPx(keyboard.buttonSize) + margin -> 1
            x <= 2 * dpToPx(keyboard.buttonSize) + 3 * margin -> 2
            else -> 3
        }
    }

    fun storeAnalysis(context: Context) {
//        val fileName =
//            if (keyboard.isRandomized) "miss_clicks_randomized.csv" else "miss_clicks.csv"
//        try {
//            val file = File(context.filesDir, fileName)
        for (missClick in keyboard.missClicks) {
            // file.appendText(analyzeMissClick(missClick))
            analyzeMissClick(missClick)
        }
//            file.appendText("\n")
//        } catch (e: IOException) {
//            Log.e(TAG, "Error closing file writer: ${e.message}")
//            e.printStackTrace()
//        }
        resetTapData()
    }

    private fun analyzeMissClick(missClick: Pair<Float, Float>): String {
        val x = missClick.first
        val y = missClick.second
        val column = getColumn(x)
        val row = getRow(y)
        val number = getMissClickedNumber(column, row)
        println("[$x, $y], column = $column, row = $row, intended number: $number \n")
        return "[$x, $y], column = $column, row = $row, intended number: $number \n"
    }

    private fun getMissClickedNumber(column: Int, row: Int): Int {
        return keyboard.layout[row - 1][column - 1].toInt()
    }

    companion object {
        private const val MAX_NUM_LENGTH = 10
        private const val MAX_PIN_LENGTH = 4
        private const val MAX_DECIMAL_LENGTH = 2
        private const val CORRECT_PIN = "1234"
    }
}