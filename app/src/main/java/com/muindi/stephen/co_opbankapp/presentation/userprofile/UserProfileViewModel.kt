package com.muindi.stephen.co_opbankapp.presentation.userprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muindi.stephen.co_opbankapp.domain.repository.CardsRepository
import com.muindi.stephen.co_opbankapp.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val repository: CardsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UserProfileUiState(isLoading = true))
    val state: StateFlow<UserProfileUiState> = _state


    fun fetchUserDetails() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
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
                            error = result.message ?: "Failed to load user profile, unexpected error occurred."
                        )
                    }

                    else -> Unit
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Unknown error occurred."
                )
            }
        }
    }
}