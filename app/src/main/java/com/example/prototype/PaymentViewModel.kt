package com.example.prototype

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.prototype.data.KeyboardData
import com.example.prototype.data.PaymentState
import com.example.prototype.keyboard.KeyboardAction
import com.example.prototype.keyboard.KeyboardAnalysis
import com.example.prototype.root.clickResponse


/**
 * ViewModel class that manages the [state] and logic related to payment processing
 * and [keyboard] for miss-click management and its further analysis.
 */
class PaymentViewModel : ViewModel() {

    var state by mutableStateOf(PaymentState())
    var keyboard by mutableStateOf(KeyboardData())

    fun onAction(action: KeyboardAction, context: Context) {
        if ((action !is KeyboardAction.MissClick) && (action !is KeyboardAction.Layout)) {
            clickResponse(context)
        }
        when (action) {
            is KeyboardAction.Number -> enterNumber(action.number, action.isPinScreen)
            is KeyboardAction.Delete -> deleteNumber()
            is KeyboardAction.Decimal -> enterDecimal()
            is KeyboardAction.MissClick -> recordCoordinates(action.x, action.y)
            is KeyboardAction.Layout -> setKeyboard(action.isRandomized, action.layout)
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

    fun isCorrectPin(): Boolean {
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

    fun deletePin() {
        state = state.copy(pin = "")
    }

    private fun deleteNumber() {
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
            if (state.pin.length == MAX_PIN_LENGTH)
                return
            state = state.copy(pin = state.pin + number)
            return
        }

        if (state.price.contains(".")) {
            return enterDecimal(number)
        }

        if ((state.price.length >= MAX_NUM_LENGTH))
            return

        state = if (state.price == "0") {
            state.copy(price = "" + number)
        } else {
            state.copy(price = state.price + number)
        }
        formatPriceString()
    }

    private fun setKeyboard(isRandomized: Boolean, layout: Array<Array<String>>) {
        keyboard = keyboard.copy(
            isRandomized = isRandomized,
            layout = layout
        )
    }

    private fun recordCoordinates(x: Float, y: Float) {
        keyboard = keyboard.copy(missClicks = keyboard.missClicks + Pair(x, y))
    }

    fun analysis(context: Context) {
        KeyboardAnalysis(keyboard).storeAnalysis(context)
        resetTapData()
    }

    companion object {
        private const val MAX_NUM_LENGTH = 10
        private const val MAX_PIN_LENGTH = 4
        private const val MAX_DECIMAL_LENGTH = 2
        private const val CORRECT_PIN = "1234"
    }
}