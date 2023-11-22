package com.example.prototype.root

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.prototype.data.PaymentState
import com.example.prototype.screens.GetIcon
import com.example.prototype.ui.theme.DarkGrey
import com.example.prototype.ui.theme.LightBlue
import com.example.prototype.ui.theme.Typography
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun PlayTone(id: Int) {
    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer.create(context, id) }
    var tonePlayed by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!tonePlayed) {
            tonePlayed = true
            mediaPlayer.setOnCompletionListener { mediaPlayer.release() }
            mediaPlayer.start()
        }
    }
}


/**
 * Represents a layout for the payment success/failed screen
 * with adjustable [upperSpace] and [content].
 */
@Composable
fun ResultScreenLayout(upperSpace: Int, content: @Composable () -> Unit) {
    Box(modifier = Modifier.padding(top = upperSpace.dp)) {
        Box(
            modifier = Modifier
                .padding(top = 70.dp)
                .fillMaxSize()
                .gradient()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .screenPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            content()
        }
    }
}


@Composable
fun IconWithBackground(icon: ImageVector, backgroundColor: Color) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .clip(CircleShape)
                .fillMaxSize()
                .background(backgroundColor)
                .padding(15.dp)
        ) {
            GetIcon(icon)
        }
    }
}


/**
 * Represents a navigation button with customizable properties.
 */
@Composable
fun NavigationButton(
    color: Color,
    text: String,
    onClick: () -> Unit = {},
    enabled: Boolean = true
) {
    val context = LocalContext.current
    Button(
        enabled = enabled,
        onClick = {
            onClick()
            clickResponse(context)
        },
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        modifier = Modifier.fillMaxWidth(),
        elevation = null
    ) {
        Text(
            text = text,
            style = Typography.button,
            color = if (!enabled) DarkGrey else Color.White
        )
    }
}


@Composable
fun PaymentInfo(state: PaymentState) {
    Column(
        modifier = Modifier.height(40.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        GenerateRow("Platba  ", state.price, "  EUR")
        Divider(color = LightBlue)
    }
}


/**
 * Generates a row with three strings displayed in separate Text elements.
 */
@Composable
fun GenerateRow(string1: String, string2: String, string3: String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = string1,
            style = Typography.h4
        )
        Text(
            text = string2,
            style = Typography.h3,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)

        )
        Text(
            text = string3.orEmpty(),
            style = Typography.h4
        )
    }
}

/**
 * Displays payment information and the current date and time of the transaction.
 */
@Composable
fun TransactionDetails(state: PaymentState) {
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat("dd. MM. yyyy,  HH:mm", Locale.getDefault())
    val current = formatter.format(time)

    PaymentInfo(state = state)
    Spacer(modifier = Modifier.height(5.dp))
    GenerateRow("Dátum  ", current.toString(), null)
}