package com.example.prototype.screens

import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.prototype.data.PaymentState
import com.example.prototype.keyboard.Keyboard
import com.example.prototype.keyboard.KeyboardAction
import com.example.prototype.root.PaymentInfo
import com.example.prototype.root.horizontalScreenPadding
import com.example.prototype.root.verticalScreenPadding
import com.example.prototype.root.vibrate
import com.example.prototype.ui.theme.*

@Composable
fun EnterPinScreen(
    state: PaymentState,
    onAction: (KeyboardAction, Context) -> Unit,
    onConfirmButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScreenPadding(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.horizontalScreenPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            PaymentInfo(state = state)
            PinScreenText(state = state)
            PinDots(state = state)
        }
        Column {
            Keyboard(onAction = onAction, forPinScreen = true, isRandomized = true)
            PinButtons(
                state,
                onConfirmButtonClicked,
                onBackButtonClicked,
                onCancelButtonClicked
            )
        }
    }
}

// TEXT
@Composable
fun GenerateText(title: String, subtitle: String?) {
    Column(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = title, style = Typography.h2, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(6.dp))
        if (!subtitle.isNullOrBlank()) {
            Text(text = subtitle, style = Typography.h3, fontWeight = FontWeight.Normal)
        }
    }
}


@Composable
fun PinScreenText(state: PaymentState) {
    when (state.pinAttempts) {
        3 -> GenerateText(title = "Zadajte PIN", subtitle = null)
        2 -> GenerateText(title = "Nesprávny PIN", subtitle = "Skúste znova.")
        else -> GenerateText(title = "Nesprávny PIN", subtitle = "Zostáva posledný pokus.")
    }
}


// PIN DOTS
@Composable
fun PinDots(state: PaymentState) {
    val context = LocalContext.current
    val offsetX = remember { Animatable(0f) }
    var color by remember { mutableStateOf(LightGrey) }

    LaunchedEffect(state.pinAttempts) {
        if (state.pinAttempts != 3) {
            vibrate(context, 400)
            color = Red
            var left = true
            for (i in 1..3) {
                offsetX.animateTo(targetValue = if (left) 10f else -10f, tween(100))
                left = !left
            }
            offsetX.animateTo(0f)
            color = LightGrey
        }
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(40.dp),
        modifier = Modifier.offset(x = offsetX.value.dp)
    ) {
        GenerateDots(state = state, color = color)
    }
}

@Composable
fun GenerateDots(state: PaymentState, color: Color, pinLength: Int = 4) {
    for (i in 0 until state.pin.length) {
        Dot(color = LightBlue)
    }
    for (i in state.pin.length until pinLength) {
        Dot(color = color)
    }
}

@Composable
fun Dot(color: Color) {
    Box(
        modifier = Modifier
            .size(15.dp)
            .clip(CircleShape)
            .background(color)
    )
}


// BUTTONS
@Composable
fun CancelButton(
    onCancelButtonClicked: () -> Unit
) {
    Button(
        shape = CancelBubble,
        modifier = Modifier.size(height = 48.dp, width = 110.dp),
        elevation = null,
        colors = ButtonDefaults.buttonColors(backgroundColor = Red),
        onClick = { onCancelButtonClicked() })
    {
        GetIcon(Icons.Rounded.Close)
    }
}

@Composable
fun BackButton(
    onBackButtonClicked: () -> Unit,
) {
    Button(
        shape = CircleShape,
        modifier = Modifier.size(62.dp),
        elevation = null,
        colors = ButtonDefaults.buttonColors(backgroundColor = Yellow),
        onClick = { onBackButtonClicked() })
    {
        GetIcon(Icons.Rounded.ArrowBack)
    }
}

@Composable
fun ConfirmButton(
    state: PaymentState,
    onConfirmButtonClicked: () -> Unit,
) {
    Button(
        enabled = state.pin.length >= 4,
        shape = ConfirmBubble,
        modifier = Modifier.size(height = 48.dp, width = 110.dp),
        elevation = null,
        colors = ButtonDefaults.buttonColors(backgroundColor = Green),
        onClick = { onConfirmButtonClicked() })
    {
        GetIcon(Icons.Rounded.Done)
    }
}

@Composable
fun GetIcon(image: ImageVector) {
    Icon(
        imageVector = image,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier.fillMaxSize()
    )
}


@Composable
fun PinButtons(
    state: PaymentState,
    onConfirmButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalScreenPadding.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CancelButton(onCancelButtonClicked)
        BackButton(onBackButtonClicked)
        ConfirmButton(state, onConfirmButtonClicked)
    }
}
