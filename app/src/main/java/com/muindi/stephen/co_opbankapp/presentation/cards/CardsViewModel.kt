package com.muindi.stephen.co_opbankapp.presentation.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muindi.stephen.co_opbankapp.domain.repository.CardsRepository
import com.muindi.stephen.co_opbankapp.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val repository: CardsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CardsUiState())
    val state = _state.asStateFlow()

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
}