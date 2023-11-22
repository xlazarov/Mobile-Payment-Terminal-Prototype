package com.example.prototype.keyboard

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.prototype.data.KeyboardData
import com.example.prototype.root.buttonSize
import com.example.prototype.root.dpToPx
import com.example.prototype.root.screenWidth
import java.io.File
import java.io.IOException

/**
 *
 */
class KeyboardAnalysis(private val keyboard: KeyboardData) {

    /**
     * Returns the distance between keypad buttons in Px,
     * based on a device specific [screenWidth] and [buttonSize] of the keypad.
     */
    private fun getButtonMargin(): Float =
        (screenWidth - 3 * dpToPx(buttonSize)) / 4


    /**
     * Returns a number of a [keyboard] column based on [x] axis value.
     */
    private fun getColumn(x: Float): Int {
        val margin = getButtonMargin()
        return when {
            x <= dpToPx(buttonSize) + margin -> 0
            x <= 2 * dpToPx(buttonSize) + 3 * margin -> 1
            else -> 2
        }
    }


    /**
     * Returns a number of a [keyboard] row based on [y] axis value.
     */
    private fun getRow(y: Float): Int {
        return when {
            y <= dpToPx(buttonSize) -> 0
            y <= 2 * dpToPx(buttonSize) -> 1
            y <= 3 * dpToPx(buttonSize) -> 2
            else -> 3
        }
    }


    /**
     * Returns a String of obtained information od particular [missClick].
     */
    private fun analyzeMissClick(missClick: Pair<Float, Float>): String {
        val x = missClick.first
        val y = missClick.second
        val column = getColumn(x)
        val row = getRow(y)
        val number = getMissClickedNumber(column, row)
        return "${column + 1},${row + 1},$number\n"
    }


    /**
     * Returns a number that was intended to hit based on [column] and [row] of saved [layout].
     */
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


    /**
     * Stores the miss-click information into external memory of the device as a csv file.
     */
    fun storeAnalysis(context: Context) {
        val fileName =
            if (keyboard.isRandomized) "miss_clicks_randomized.csv" else "miss_clicks.csv"
        try {
            val externalFilesDir = context.getExternalFilesDir(null)
            val destinationFile = File(externalFilesDir, fileName)
            for (missClick in keyboard.missClicks) {
                destinationFile.appendText(analyzeMissClick(missClick))
                analyzeMissClick(missClick)
            }
            destinationFile.appendText("\n")
        } catch (e: IOException) {
            Log.e(TAG, "Error closing file writer: ${e.message}")
            e.printStackTrace()
        }
    }
}