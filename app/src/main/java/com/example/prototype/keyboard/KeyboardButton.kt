package com.example.prototype.keyboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.prototype.R
import com.example.prototype.ui.theme.LightBlue


@Composable
fun KeyboardButton(
    symbol: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
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
                textAlign = TextAlign.Center,
               // style = TextStyle(fontFamily = FontFamily.Monospace)
            )
        }
    }
}
