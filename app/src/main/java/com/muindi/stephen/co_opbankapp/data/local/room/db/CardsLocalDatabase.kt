package com.muindi.stephen.co_opbankapp.data.local.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.muindi.stephen.co_opbankapp.data.dto.responses.Card
import com.muindi.stephen.co_opbankapp.data.dto.responses.GetUserResponse
import com.muindi.stephen.co_opbankapp.data.dto.responses.Transaction
import com.muindi.stephen.co_opbankapp.data.local.room.converters.WalletListConverter
import com.muindi.stephen.co_opbankapp.data.local.room.dao.CardDao
import com.muindi.stephen.co_opbankapp.data.local.room.dao.TransactionsDao
import com.muindi.stephen.co_opbankapp.data.local.room.dao.UserProfileDao

@Database(entities = [Card::class, Transaction::class, GetUserResponse::class], version = 1)
@TypeConverters(WalletListConverter::class)
abstract class CardsLocalDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao
    abstract fun transactionDao(): TransactionsDao
    abstract fun userProfileDao(): UserProfileDao
}