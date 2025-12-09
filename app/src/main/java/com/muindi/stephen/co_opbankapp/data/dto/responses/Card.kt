package com.muindi.stephen.co_opbankapp.data.dto.responses

data class Card(
    val balance: Double,
    val cardNumber: String,
    val creditLimit: Double,
    val currency: String,
    val currentSpend: Double,
    val dueDate: String,
    val expiryDate: String,
    val holderName: String,
    val id: String,
    val linkedAccountName: String,
    val name: String,
    val status: String,
    val type: String,
    val userId: String,
    val wallets: List<Wallet>
)