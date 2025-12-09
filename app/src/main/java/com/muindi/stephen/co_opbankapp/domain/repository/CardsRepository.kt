package com.muindi.stephen.co_opbankapp.domain.repository

import com.muindi.stephen.co_opbankapp.data.dto.responses.Card
import com.muindi.stephen.co_opbankapp.data.dto.responses.GetUserResponse
import com.muindi.stephen.co_opbankapp.data.dto.responses.Transaction
import com.muindi.stephen.co_opbankapp.domain.utils.Resource

/**
 * Repository definition
 */
interface CardsRepository {
    suspend fun getCards(): Resource<List<Card>>
    suspend fun getTransactions(cardId: String, limit: Int = 10): Resource<List<Transaction>>
    suspend fun getUser(): Resource<GetUserResponse>
}