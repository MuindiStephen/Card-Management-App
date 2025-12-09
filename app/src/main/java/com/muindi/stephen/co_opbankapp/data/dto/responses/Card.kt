package com.muindi.stephen.co_opbankapp.data.dto.responses

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.muindi.stephen.co_opbankapp.data.local.room.converters.WalletListConverter

@Entity(tableName = "cards")
@TypeConverters(WalletListConverter::class)
data class Card(
    val balance: Double,
    val cardNumber: String? = null,
    val creditLimit: Double,
    val currency: String ?= null,
    val currentSpend: Double,
    val dueDate: String? = null,
    val expiryDate: String? = null,
    val holderName: String? = null,
    @PrimaryKey val id: String,
    val linkedAccountName: String? = null,
    val name: String? = null,
    val status: String? = null,
    val type: String? = null,
    val userId: String? = null,
    val wallets: List<Wallet>? = null
)