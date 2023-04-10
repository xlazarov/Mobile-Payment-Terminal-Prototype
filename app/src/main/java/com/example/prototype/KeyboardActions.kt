package com.example.prototype

sealed class ButtonAction {
    data class Number(val number: Int, val pinScreen: Boolean) : ButtonAction()
    data class Delete(val pinScreen: Boolean) : ButtonAction()
    object Decimal : ButtonAction()
    data class MissClick(val x: Float, val y: Float) : ButtonAction()
}

