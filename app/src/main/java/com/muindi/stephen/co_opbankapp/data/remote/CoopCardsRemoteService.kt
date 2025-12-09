package com.muindi.stephen.co_opbankapp.data.remote

import com.muindi.stephen.co_opbankapp.data.dto.responses.AllCardsResponse
import com.muindi.stephen.co_opbankapp.data.dto.responses.CardTransactionsResponse
import com.muindi.stephen.co_opbankapp.data.dto.responses.GetUserResponse
import retrofit2.http.GET

/**
 * Remote service interface for endpoints
 */
interface CoopCardsRemoteService {

    @GET("getUser")
    suspend fun getUser(): GetUserResponse

    @GET("getCards")
    suspend fun getCards(): AllCardsResponse

    @GET("cardTransactions")
    suspend fun getCardTransactions(): CardTransactionsResponse
}