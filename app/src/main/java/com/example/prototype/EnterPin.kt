package com.example.prototype

import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prototype.ui.theme.*

@Composable
fun EnterPinScreen(
    state: PaymentState,
    onAction: (KeyboardAction, Context) -> Unit,
    onConfirmButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 35.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 22.dp)
        ) {
            PaymentInfo(state)
            Spacer(modifier = Modifier.height(10.dp))
            PinScreenTitle(state)
            Spacer(modifier = Modifier.height(10.dp))
            PinDots(state)
        }
        Column {
            RandomizedKeyboard(
                onAction = onAction
            )
            PinButtons(state, onAction, onConfirmButtonClicked, onCancelButtonClicked)
        }
    }
}

@Composable
fun TitleText(title: String, subtitle: String?) {
    Column(
        modifier = Modifier.height(120.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            fontSize = 23.sp,
            fontWeight = FontWeight.SemiBold
        )
        if (subtitle != null) {
            Text(
                text = subtitle,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
    }
}

@Composable
fun PinScreenTitle(state: PaymentState) {
    when (state.pinAttempts) {
        3 -> TitleText(title = "Zadajte PIN", subtitle = null)
        2 -> TitleText(title = "Nesprávny PIN", subtitle = "Skúste znova.")
        else -> TitleText(title = "Nesprávny PIN", subtitle = "Zostáva posledný pokus.")
    }
}

@Composable
fun PinButtons(
    state: PaymentState,
    onAction: (KeyboardAction, Context) -> Unit,
    onConfirmButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            enabled = true,
            shape = RoundedCornerShape(30.dp).copy(
                bottomEnd = ZeroCornerSize
            ),
            modifier = Modifier.size(height = 48.dp, width = 110.dp),
            elevation = null,
            colors = ButtonDefaults.buttonColors(backgroundColor = Red),
            onClick = { onCancelButtonClicked() })
        {
            Icon(
                Icons.Rounded.Close,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.width(17.dp))
        Button(
            enabled = true,
            shape = CircleShape,
            modifier = Modifier.size(62.dp),
            elevation = null,
            colors = ButtonDefaults.buttonColors(backgroundColor = Yellow),
            onClick = { onAction(KeyboardAction.Delete(true), context) })
        {
            Icon(
                Icons.Rounded.ArrowBack,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.width(17.dp))
        Button(
            enabled = state.pin.length >= 4,
            shape = RoundedCornerShape(30.dp).copy(
                bottomStart = ZeroCornerSize
            ),
            modifier = Modifier.size(height = 48.dp, width = 110.dp),
            elevation = null,
            colors = ButtonDefaults.buttonColors(backgroundColor = Green),
            onClick = { onConfirmButtonClicked() })
        {
            Icon(
                Icons.Rounded.Done,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Composable
fun PaymentInfo(state: PaymentState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(35.dp)
    ) {
        Text(
            text = "Platba  ",
            color = LightBlue,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = state.price,
            textAlign = TextAlign.End,
            fontSize = 20.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = " EUR",
            color = LightBlue,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
    TabRowDefaults.Divider(
        thickness = 1.dp,
        color = BabyBlue
    )
}

@Composable
fun PinDots(state: PaymentState) {
    val context = LocalContext.current
    val count: Int = state.pin.length
    val wrongPin: Boolean = state.pinAttempts < 3 && state.pin.isBlank()
    val offsetX = remember { Animatable(0f) }
    var color by remember { mutableStateOf(LightGrey) }

    LaunchedEffect(key1 = wrongPin) {
        if (wrongPin) {
            vibration(context)
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
        modifier = Modifier
            .height(50.dp)
            .offset(x = offsetX.value.dp)
    ) {
        for (i in 0 until count) {
            Box(
                modifier = Modifier
                    .size(15.dp)
                    .clip(CircleShape)
                    .background(LightBlue)
            )
        }
        for (i in count until 4) {
            Box(
                modifier = Modifier
                    .size(15.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}
