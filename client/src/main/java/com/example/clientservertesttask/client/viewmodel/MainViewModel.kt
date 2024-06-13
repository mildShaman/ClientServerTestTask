package com.example.clientservertesttask.client.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientservertesttask.client.model.client.Client
import com.example.common.BrowserOpenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val client: Client
) : ViewModel() {
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

    fun onBrowserOpen(time: Long) {
        viewModelScope.launch((Dispatchers.IO)) {
            client.sendBrowserOpenEvent(BrowserOpenEvent(time, true))
        }
    }

    fun disconnectFromServer() {
        viewModelScope.launch {
            client.disconnect()
        }
    }
}