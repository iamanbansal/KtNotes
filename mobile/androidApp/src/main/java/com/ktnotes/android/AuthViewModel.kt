package com.ktnotes.android

import com.ktnotes.feature.auth.presentation.AuthSharedViewModel
import com.ktnotes.feature.auth.remote.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
public class AuthViewModel @Inject constructor(repository: AuthRepository) :
    AuthSharedViewModel(repository) {

    private val _fieldState = MutableStateFlow(LoginFieldsState())
    val fieldsState = _fieldState.asStateFlow()


    fun onNameChanged(name: String) = _fieldState.update { it.copy(name = name) }
    fun onEmailChanged(email: String) = _fieldState.update { it.copy(email = email) }
    fun onPasswordChange(pass: String) = _fieldState.update { it.copy(pass = pass) }


    fun onLoginClick() = with(fieldsState.value) {
        login(email, pass)
    }

    fun onSignupClick() = with(fieldsState.value) {
        register(name, email, pass)
    }
}

