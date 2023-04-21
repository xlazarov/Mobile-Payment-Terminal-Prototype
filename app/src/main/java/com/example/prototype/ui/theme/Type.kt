package com.example.prototype.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.prototype.R


val fontFamily = FontFamily(
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_regular, FontWeight.Normal)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 30.sp,
        color = DarkFont
    ),
    button = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = Color.White
    )
)



/*
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        color = DarkFont
    ),
    h2 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        color = DarkFont
    ),
    h3 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        color = DarkFont
    ),
    keypad = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Regular,
        fontSize = 28.sp,
        color = DarkFont
    ),
    button = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        color = Color.White
    )
)
*/
