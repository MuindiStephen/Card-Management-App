package com.muindi.stephen.co_opbankapp.data.dto.responses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    val amount: Double,
    val cardId: String,
    val currency: String,
    val date: String,
    @PrimaryKey val id: String,
    val merchant: String,
    val type: String
)