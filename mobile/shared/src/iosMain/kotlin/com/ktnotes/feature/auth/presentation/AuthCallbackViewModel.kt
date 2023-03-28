package com.ktnotes.feature.auth.presentation

import com.ktnotes.feature.auth.remote.AuthRepository
import com.ktnotes.viewmodel.asCallbacks

class AuthCallbackViewModel(authRepository: AuthRepository) {
    private val viewModel: AuthSharedViewModel = AuthSharedViewModel(authRepository)

    val stateAdapter = viewModel.state.asCallbacks(viewModel.viewModelScope)

    fun login(email: String, password: String) {
        viewModel.login(email, password)
    }

    fun register(name: String, email: String, password: String) {
        viewModel.register(name, email, password)
    }

    fun clear(){
        viewModel.clear()
    }
}