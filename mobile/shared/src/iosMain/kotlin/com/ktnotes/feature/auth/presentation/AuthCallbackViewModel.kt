package com.ktnotes.feature.auth.presentation

import com.ktnotes.feature.auth.remote.AuthRepository
import com.ktnotes.viewmodel.ViewModel
import com.ktnotes.viewmodel.asCallbacks

class AuthCallbackViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val viewModel: AuthSharedViewModel = AuthSharedViewModel(authRepository)

    val state = viewModel.state.asCallbacks(viewModelScope)

    fun login(email: String, password: String) {
        viewModel.login(email, password)
    }

    fun register(name: String, email: String, password: String) {
        viewModel.register(name, email, password)
    }
}