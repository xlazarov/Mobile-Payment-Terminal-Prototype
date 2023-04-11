package com.example.prototype

data class PaymentState(
    val price: String = "0",
    val pin: String = "",
    val pinAttempts: Int = 3,
)