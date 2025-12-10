package com.muindi.stephen.co_opbankapp.data.repository

import com.muindi.stephen.co_opbankapp.data.dto.responses.Card
import com.muindi.stephen.co_opbankapp.data.dto.responses.GetUserResponse
import com.muindi.stephen.co_opbankapp.data.dto.responses.Transaction
import com.muindi.stephen.co_opbankapp.data.local.room.dao.CardDao
import com.muindi.stephen.co_opbankapp.data.local.room.dao.TransactionsDao
import com.muindi.stephen.co_opbankapp.data.local.room.dao.UserProfileDao
import com.muindi.stephen.co_opbankapp.data.mappers.toGetUserResponseEntity
import com.muindi.stephen.co_opbankapp.data.remote.CoopCardsRemoteService
import com.muindi.stephen.co_opbankapp.domain.repository.CardsRepository
import com.muindi.stephen.co_opbankapp.domain.utils.Resource
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

/**
 * Repository Implementation
 */
class CardRepositoryImpl @Inject constructor (
    private val api: CoopCardsRemoteService,
    private val cardDao: CardDao,
    private val transactionDao: TransactionsDao,
    private val userProfileDao: UserProfileDao,
) : CardsRepository {
    override suspend fun getCards(): Resource<List<Card>> {
        return  try {
            return try {
                val localCards = cardDao.getAllCards().firstOrNull()
                if (!localCards.isNullOrEmpty()) {
                    Resource.Data(localCards)
                } else {
                    // Fetching from remote API and saving to room db
                    val response = api.getCards()
                    val entities = response.cards
                    cardDao.insertCards(entities)
                    Resource.Data(response.cards)
                }
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage ?: "Unexpected error occurred")
            }

        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unexpected error occurred")
        }
    }

    override suspend fun getTransactions(cardId: String, limit: Int): Resource<List<Transaction>> {
        return try {
            val local = transactionDao.getTransactionsForCard(cardId).firstOrNull()
            if (!local.isNullOrEmpty()) {
                Resource.Data(local)
            } else {
                val response = api.getCardTransactions()
                val transactions = response.transactions
                transactionDao.insertTransactions(transactions)
                Resource.Data(response.transactions)
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unexpected error occurred")
        }
    }

    override suspend fun getUser(): Resource<GetUserResponse> {
        return try {

            val localUserInRoomDB = userProfileDao.getUser().firstOrNull()

            if (localUserInRoomDB != null) {
                return Resource.Data(localUserInRoomDB)
            }

            val responseRemoteUser = api.getUser()
            val saveRemoteUserToRoom = responseRemoteUser.user.toGetUserResponseEntity()
            userProfileDao.insertUser(saveRemoteUserToRoom)

            Resource.Data(saveRemoteUserToRoom)

        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unexpected error has occurred.")
        }
    }
}