package com.example.prototype.root

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prototype.data.PaymentState
import com.example.prototype.ui.theme.BlueButton
import com.example.prototype.ui.theme.DarkGrey
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NavigationButton(color: Color, text: String, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        enabled = true,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        modifier = Modifier.fillMaxWidth(),
        elevation = null
    ) {
        Text(text)
    }
}

@Composable
fun TransactionResult(result: String, details: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = result,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = details,
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
    }
}

@Composable
fun TransactionDetails(state: PaymentState) {
    Row{
        Text(
            text = "SUMA",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = state.price + " EUR",
            textAlign = TextAlign.End,
            color = BlueButton,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    TabRowDefaults.Divider(
        thickness = 1.dp,
        color = DarkGrey
    )
    Spacer(modifier = Modifier.height(8.dp))
    Row {
        Text(
            text = "DÁTUM PLATBY",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
        )
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault())
        val current = formatter.format(time)
        Text(
            text = current.toString(),
            textAlign = TextAlign.End,
            color = BlueButton,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
    }
}