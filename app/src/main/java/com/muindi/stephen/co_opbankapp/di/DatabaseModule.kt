package com.muindi.stephen.co_opbankapp.di

import android.content.Context
import androidx.room.Room
import com.muindi.stephen.co_opbankapp.data.local.room.dao.CardDao
import com.muindi.stephen.co_opbankapp.data.local.room.dao.TransactionsDao
import com.muindi.stephen.co_opbankapp.data.local.room.dao.UserProfileDao
import com.muindi.stephen.co_opbankapp.data.local.room.db.CardsLocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule  {
    @Provides
    @Singleton
    fun provideCardsLocalRoomDatabase(@ApplicationContext context: Context): CardsLocalDatabase {
        return Room.databaseBuilder(
                context,
                CardsLocalDatabase::class.java,
                "coopbank_cards_local_db"
            ).fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    fun provideCardDao(db: CardsLocalDatabase): CardDao {
        return db.cardDao()
    }

    @Provides
    fun provideTransactionDao(db: CardsLocalDatabase): TransactionsDao {
        return db.transactionDao()
    }

    @Provides
    fun provideUserDao(db: CardsLocalDatabase): UserProfileDao {
        return db.userProfileDao()
    }
}