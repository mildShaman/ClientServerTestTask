package com.example.clientservertesttask.client.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clientservertesttask.client.model.client.Client
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val client: Client
): ViewModel() {
    private val _isClientActive = MutableStateFlow(false)
    val isClientActive: StateFlow<Boolean> = _isClientActive

    init {
        viewModelScope.launch {
            client.isActive.collect {
                _isClientActive.value = it
            }
        }
    }

    fun connectToServer() {
        if (!_isClientActive.value) {
            viewModelScope.launch {
                client.connect()
            }
        }
    }

    fun disconnectFromServer() {
        if (_isClientActive.value) {
            viewModelScope.launch {
                client.disconnect()
            }
        }
    }
}