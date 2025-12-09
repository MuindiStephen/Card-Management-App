package com.muindi.stephen.co_opbankapp.data.dto.responses

data class Transaction(
    val amount: Double,
    val cardId: String,
    val currency: String,
    val date: String,
    val id: String,
    val merchant: String,
    val type: String
)