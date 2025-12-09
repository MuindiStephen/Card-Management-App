package com.muindi.stephen.co_opbankapp.presentation

import com.muindi.stephen.co_opbankapp.data.dto.responses.Card
import com.muindi.stephen.co_opbankapp.data.dto.responses.GetUserResponse
import com.muindi.stephen.co_opbankapp.data.dto.responses.Transaction

data class CardsUiState(
    val isLoading: Boolean = false,
    val cards: List<Card> = emptyList(),
    val user: GetUserResponse? = null,
    val transactions: List<Transaction> = emptyList(),
    val error: String? = null
)

data class CardDetailsUiState(
    val isLoading: Boolean = false,
    val card: Card? = null,
    val transactions: List<Transaction> = emptyList(),
    val userProfile: GetUserResponse? = null,
    val isBalanceVisible: Boolean = true
)

