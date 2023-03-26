package com.ktnotes.android.feature.auth

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ktnotes.android.AuthViewModel

@Composable
fun RegistrationScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onLoggedIn: () -> Unit
) {
    val name by rememberSaveable { viewModel.name }
    val email by rememberSaveable { viewModel.email }
    val password by rememberSaveable { viewModel.password }
    var isLoginMode by remember { mutableStateOf(true) }


    val loginUiState by viewModel.state.collectAsState()

    Log.d("**main", "RegistrationScreen() called with: viewModel = $loginUiState")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!isLoginMode) {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    viewModel.name.value = it
                },
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }

        OutlinedTextField(
            value = email,
            singleLine = true,
            onValueChange = {
                viewModel.email.value = it
            },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = password,
            singleLine = true,
            onValueChange = {
                viewModel.password.value = it
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Button(
            onClick = {
                if (isLoginMode) {
                    viewModel.login()
                } else {
//                    viewModel.register(name, email, password)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            enabled = loginUiState.isLoading.not()
        ) {
            if (loginUiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.CenterVertically),
                )
            } else {
                Text(if (isLoginMode) "Log in" else "Sign up")
            }
        }

        TextButton(
            onClick = { isLoginMode = !isLoginMode },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                if (isLoginMode) "Don't have an account? Sign up" else "Already have an account? Log in",
                textDecoration = TextDecoration.Underline
            )
        }
    }


    if (loginUiState.isLoggedIn) {
        onLoggedIn()
    }
}