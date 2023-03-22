package com.ktnotes.android

import androidx.compose.runtime.mutableStateOf
import com.ktnotes.feature.auth.presentation.AuthSharedViewModel
import com.ktnotes.feature.auth.remote.AuthRepository


class AuthViewModel(private val repository: AuthRepository) : AuthSharedViewModel(repository) {

    var name = mutableStateOf("")
    var email = mutableStateOf("")
    var password = mutableStateOf("")

    fun  login(){
        login(email.value, password.value)
    }
}