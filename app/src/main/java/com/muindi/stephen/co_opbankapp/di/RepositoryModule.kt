package com.muindi.stephen.co_opbankapp.di

import com.muindi.stephen.co_opbankapp.data.local.room.dao.CardDao
import com.muindi.stephen.co_opbankapp.data.local.room.dao.TransactionsDao
import com.muindi.stephen.co_opbankapp.data.local.room.dao.UserProfileDao
import com.muindi.stephen.co_opbankapp.data.remote.CoopCardsRemoteService
import com.muindi.stephen.co_opbankapp.data.repository.CardRepositoryImpl
import com.muindi.stephen.co_opbankapp.domain.repository.CardsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCardsRepository(
        api: CoopCardsRemoteService,
        cardDao: CardDao,
        transactionsDao: TransactionsDao,
        userProfileDao: UserProfileDao
    ): CardsRepository {
        return CardRepositoryImpl(
            api, cardDao,transactionsDao,userProfileDao
        )
    }
}