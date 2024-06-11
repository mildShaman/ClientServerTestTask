package com.example.clientservertesttask.server.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientservertesttask.server.model.server.Server
import com.example.clientservertesttask.server.model.settings.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val server: Server
): ViewModel() {
    private val _isServerStarted = MutableStateFlow(false)
    val isServerStarted: StateFlow<Boolean> = _isServerStarted

    init {
        viewModelScope.launch(context = Dispatchers.IO) {
            server.isStarted.collect {
                _isServerStarted.value = it
            }
        }
    }

    fun startServer() {
        if (!_isServerStarted.value) {
            viewModelScope.launch {
                server.start()
            }
        }
    }

    fun stopServer() {
        if (_isServerStarted.value) {
            viewModelScope.launch {
                server.stop()
            }
        }
    }
}