package com.example.prototype.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.prototype.ui.theme.PrototypeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            screenWidth = getScreenWidthPx()
            PrototypeTheme {
                // A surface container using the 'background' color from the theme
                PaymentApp()
            }
        }
    }
}


