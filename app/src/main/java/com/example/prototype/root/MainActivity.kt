package com.example.prototype.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.prototype.ui.theme.PrototypeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

        setContent {
            screenWidth = screenWidthPx()

            // Keyboard setting for user testing
            isRandomized = false
            buttonSize = 90.dp

            PrototypeTheme {
                PaymentApp()
            }
        }
    }
}


