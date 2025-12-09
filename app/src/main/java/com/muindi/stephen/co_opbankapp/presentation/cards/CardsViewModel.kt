package com.muindi.stephen.co_opbankapp.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muindi.stephen.co_opbankapp.domain.repository.CardsRepository
import com.muindi.stephen.co_opbankapp.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CardsRepository
) : ViewModel() {
    private val _state = mutableStateOf(CardsUiState())
    val state: State<CardsUiState> = _state

    /**
     * Loads all cards
     */
    fun fetchCards() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            when (val result = repository.getCards()) {
                is Resource.Data -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        cards = result.value ?: emptyList()
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }

                else -> Unit
            }
        }
    }

    /**
     *
     */
    fun loadUser() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            when (val result = repository.getUser()) {
                is Resource.Data -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        user = result.value
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }

                else -> Unit
            }
        }
    }

    fun fetchTransactions(cardId: String, limit: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            when (val result = repository.getTransactions(cardId, limit)) {
                is Resource.Data -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        transactions = result.value ?: emptyList()
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }

                else -> Unit
            }
        }
    }
}