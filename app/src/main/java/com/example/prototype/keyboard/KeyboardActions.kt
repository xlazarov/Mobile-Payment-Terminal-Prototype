package com.example.prototype.keyboard

sealed class KeyboardAction {
    data class Number(val number: Int, val pinScreen: Boolean) : KeyboardAction()
    object Delete : KeyboardAction()
    object Decimal : KeyboardAction()
    data class MissClick(val x: Float, val y: Float) : KeyboardAction()

    @Suppress("EqualsAndHashCode")
    data class SetKeyboard(val isRandomized: Boolean, val layout: Array<Array<String>>) :
        KeyboardAction()
}