package com.muindi.stephen.co_opbankapp.data.local.room.converters

import com.muindi.stephen.co_opbankapp.data.dto.responses.Wallet

class WalletListConverter {
    private val gson = com.google.gson.Gson()

    @androidx.room.TypeConverter
    fun fromWalletList(wallets: List<Wallet>?): String {
        return gson.toJson(wallets)
    }

    @androidx.room.TypeConverter
    fun toWalletList(walletsString: String?): List<Wallet>? {
        if (walletsString.isNullOrEmpty()) return emptyList()
        val type = com.google.gson.reflect.TypeToken.getParameterized(List::class.java, Wallet::class.java).type
        return gson.fromJson(walletsString, type)
    }
}