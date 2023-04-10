package com.example.prototype

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.prototype.ui.theme.LightBlue
import com.example.prototype.ui.theme.LightGrey


@Composable
fun KeyboardButton(
    symbol: String,
    onClick: () -> Unit,
    pinScreen: Boolean
) {

    var modifier = Modifier
        .size(70.dp)
        .clip(CircleShape)

    modifier = if (!pinScreen) {
        modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(color = LightGrey),
            onClick = onClick
        )
    } else {
        modifier.clickable(onClick = onClick)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        when (symbol) {
            "Delete" -> Icon(
                painter = painterResource(id = R.drawable.baseline_backspace_24),
                contentDescription = null,
                tint = LightBlue,
                modifier = Modifier.size(40.dp)
            )
            else -> Text(
                text = symbol
            )
        }
    }
}
