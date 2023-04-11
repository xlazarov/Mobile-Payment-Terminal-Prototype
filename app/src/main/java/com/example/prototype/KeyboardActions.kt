package com.example.prototype

sealed class KeyboardAction {
    data class Number(val number: Int, val pinScreen: Boolean) : KeyboardAction()
    data class Delete(val pinScreen: Boolean) : KeyboardAction()
    object Decimal : KeyboardAction()
    data class MissClick(val x: Float, val y: Float) : KeyboardAction()

    @Suppress("EqualsAndHashCode")
    data class Layout(val layout: Array<Array<String>>, val isRandomized: Boolean) :
        KeyboardAction()
}