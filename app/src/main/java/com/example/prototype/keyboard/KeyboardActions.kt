package com.example.prototype.keyboard


/**
 * Sealed class representing various actions performed on a keyboard.
 */
sealed class KeyboardAction {

    data class Number(val number: Int, val isPinScreen: Boolean) : KeyboardAction()

    object Delete : KeyboardAction()

    object Decimal : KeyboardAction()

    data class MissClick(val x: Float, val y: Float) : KeyboardAction()

    @Suppress("EqualsAndHashCode")
    data class Layout(val isRandomized: Boolean, val layout: Array<Array<String>>) :
        KeyboardAction()
}