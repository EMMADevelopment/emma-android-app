package com.emma.emmaandroidexample

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    // PROPERTIES
    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    // FUNCTIONS
    fun emmaIsInitialized() {
        _isReady.value = true
    }

    fun emmaIsNotInitialized() {
        _isReady.value = true
    }
}