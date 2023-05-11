package com.example.prototype.keyboard

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.prototype.data.KeyboardData
import com.example.prototype.root.dpToPx
import com.example.prototype.root.screenWidth
import java.io.File
import java.io.IOException

class KeyboardAnalysis(private val keyboard: KeyboardData) {

    private fun getButtonMargin(): Float =
        (screenWidth - 3 * dpToPx(keyboard.buttonSize)) / 4

    private fun getColumn(x: Float): Int {
        val margin = getButtonMargin()
        return when {
            x <= dpToPx(keyboard.buttonSize) + margin -> 0
            x <= 2 * dpToPx(keyboard.buttonSize) + 3 * margin -> 1
            else -> 2
        }
    }

    private fun getRow(y: Float): Int {
        return when {
            y <= dpToPx(keyboard.buttonSize) -> 0
            y <= 2 * dpToPx(keyboard.buttonSize) -> 1
            y <= 3 * dpToPx(keyboard.buttonSize) -> 2
            else -> 3
        }
    }

    private fun analyzeMissClick(missClick: Pair<Float, Float>): String {
        val x = missClick.first
        val y = missClick.second
        val column = getColumn(x)
        val row = getRow(y)
        val number = getMissClickedNumber(column, row)
        println("[$x, $y], column = ${column + 1}, row = ${row + 1}, intended number: $number \n")
        return "[$x, $y], column = ${column + 1}, row = ${row + 1}, intended number: $number \n"
    }

    private fun getMissClickedNumber(column: Int, row: Int): Int {
        if (row == 3) {
            return when (column) {
                0 -> keyboard.layout[2][0].toInt()
                1 -> keyboard.layout[3][0].toInt()
                else -> keyboard.layout[2][2].toInt()
            }
        }
        return keyboard.layout[row][column].toInt()
    }

    fun storeAnalysis(context: Context) {
        val fileName =
            if (keyboard.isRandomized) "miss_clicks_randomized.csv" else "miss_clicks.csv"
        try {
            val file = File(context.filesDir, fileName)
            for (missClick in keyboard.missClicks) {
                file.appendText(analyzeMissClick(missClick))
                analyzeMissClick(missClick)
            }
            file.appendText("\n")
        } catch (e: IOException) {
            Log.e(TAG, "Error closing file writer: ${e.message}")
            e.printStackTrace()
        }
    }
}