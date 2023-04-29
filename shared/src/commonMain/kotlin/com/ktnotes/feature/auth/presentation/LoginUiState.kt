package com.ktnotes.feature.auth.presentation

data class LoginUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val message: String? = null
)