package com.muindi.stephen.co_opbankapp.presentation.userprofile

import com.muindi.stephen.co_opbankapp.data.dto.responses.GetUserResponse


data class UserProfileUiState(
    val isLoading: Boolean = false,
    val user: GetUserResponse ? = null,
    val error: String? = null
)