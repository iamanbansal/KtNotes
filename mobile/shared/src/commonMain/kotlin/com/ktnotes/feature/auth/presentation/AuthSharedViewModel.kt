package com.ktnotes.feature.auth.presentation

import com.ktnotes.feature.auth.model.AuthRequest
import com.ktnotes.feature.auth.remote.AuthRepository
import com.ktnotes.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


open class AuthSharedViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state = _state.asStateFlow()

    fun login(email: String, password: String) {
        startLoading()
        viewModelScope.launch {
            val request = AuthRequest(email, password)
            runCatching {
                repository.login(request)
            }.onSuccess {
                _state.update { LoginUiState(isLoggedIn = true) }
            }.onFailure {throwable ->
                _state.update { LoginUiState(message = throwable.message) }
            }

        }
    }

    fun register(name: String, email: String, password: String) {
        startLoading()
        viewModelScope.launch {
            val request = AuthRequest(email, password, name)
            runCatching {
                repository.register(request)
            }.onSuccess {
                _state.update { LoginUiState(isLoggedIn = true) }
            }.onFailure {throwable ->
                _state.update { LoginUiState(message = throwable.message) }
            }
        }
    }

    private fun startLoading() {
        _state.update { LoginUiState(isLoading = true) }
    }
}