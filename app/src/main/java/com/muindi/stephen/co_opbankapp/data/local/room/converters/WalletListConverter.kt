package com.muindi.stephen.co_opbankapp.data.local.room.converters

import androidx.room.TypeConverter
import com.muindi.stephen.co_opbankapp.data.dto.responses.Wallet
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class WalletListConverter {

    private val moshi = Moshi.Builder().build()
    private val type = Types.newParameterizedType(List::class.java, Wallet::class.java)
    private val adapter = moshi.adapter<List<Wallet>>(type)

    @TypeConverter
    fun fromWalletList(wallets: List<Wallet>?): String? = wallets?.let { adapter.toJson(it) }

    @TypeConverter
    fun toWalletList(json: String?): List<Wallet>? = json?.let { adapter.fromJson(it) }
}