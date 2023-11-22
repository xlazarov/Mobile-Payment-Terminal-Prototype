package com.example.prototype.keyboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.prototype.R
import com.example.prototype.root.bounceClick
import com.example.prototype.root.buttonSize
import com.example.prototype.root.screenWidthNoMargin
import com.example.prototype.ui.theme.LightBlue
import com.example.prototype.ui.theme.Typography

/**
 * A composable function that represents a keyboard button.
 *
 * @param symbol The symbol displayed on the button.
 * @param onClick The callback function to be invoked when the button is clicked.
 */
@Composable
fun KeyboardButton(
    symbol: String,
    onClick: () -> Unit
) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(buttonSize)
            .width(screenWidthNoMargin() / 3)
            .bounceClick(onClick)
    ) {
        when (symbol) {
            "Delete" -> Icon(
                painter = painterResource(id = R.drawable.baseline_backspace_24),
                contentDescription = null,
                tint = LightBlue,
                modifier = Modifier.size(30.dp)
            )
            else -> Text(
                text = symbol,
                style = Typography.h5
            )
        }
    }
}


