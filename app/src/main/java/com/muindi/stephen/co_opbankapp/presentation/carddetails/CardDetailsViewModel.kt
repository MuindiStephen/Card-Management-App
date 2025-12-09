package com.muindi.stephen.co_opbankapp.presentation.carddetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muindi.stephen.co_opbankapp.data.dto.responses.Card
import com.muindi.stephen.co_opbankapp.data.dto.responses.Transaction
import com.muindi.stephen.co_opbankapp.domain.repository.CardsRepository
import com.muindi.stephen.co_opbankapp.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CardDetailsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val card: Card? = null,
    val transactions: List<Transaction> = emptyList(),
    val isBalanceVisible: Boolean = true,
    val userName: String = ""
)

@HiltViewModel
class CardDetailsViewModel @Inject constructor(
    private val repository: CardsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CardDetailsUiState(isLoading = true))
    val uiState: StateFlow<CardDetailsUiState> = _uiState

    fun loadCardDetails(cardId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val cardsResult = repository.getCards()

                if (cardsResult is Resource.Data
                ) {
                    val card = cardsResult.value.find { it.id == cardId }
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        card = card
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Failed to load card details"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Unexpected error occurred."
                )
            }
        }
    }

    fun loadTransactions(cardId: String) {
        viewModelScope.launch {
            try {
                val result = repository.getTransactions(cardId, limit = 20)
                if (result is Resource.Data) {
                    _uiState.value = _uiState.value.copy(transactions = result.value)
                } else {
                    _uiState.value = _uiState.value.copy(error = "Failed to load transactions")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.localizedMessage ?: "Unknown error"
                )
            }
        }
    }

    fun toggleBalanceVisibility() {
        _uiState.value = _uiState.value.copy(
            isBalanceVisible = !_uiState.value.isBalanceVisible
        )
    }
}
