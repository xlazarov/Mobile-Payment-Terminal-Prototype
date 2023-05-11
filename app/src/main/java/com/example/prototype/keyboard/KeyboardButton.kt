package com.example.prototype.keyboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.prototype.R
import com.example.prototype.root.bounceClick
import com.example.prototype.ui.theme.LightBlue
import com.example.prototype.ui.theme.Typography


@Composable
fun KeyboardButton(
    symbol: String,
    onClick: () -> Unit
) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .bounceClick()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
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


