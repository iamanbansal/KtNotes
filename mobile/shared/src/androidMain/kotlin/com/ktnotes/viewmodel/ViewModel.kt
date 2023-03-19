package com.ktnotes.viewmodel

import androidx.lifecycle.viewModelScope as androidXViewModelScope
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope


actual abstract class ViewModel actual constructor() : ViewModel() {
    actual val viewModelScope: CoroutineScope = androidXViewModelScope

    actual override fun onCleared() {
        super.onCleared()
    }
}