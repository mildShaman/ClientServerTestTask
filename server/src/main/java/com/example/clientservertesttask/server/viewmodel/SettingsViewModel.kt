package com.example.clientservertesttask.server.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientservertesttask.server.model.server.Server
import com.example.clientservertesttask.server.model.settings.AppSettings
import com.example.common.IpAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settings: AppSettings
): ViewModel() {
    private val _ipAddress = MutableStateFlow(IpAddress.DEFAULT)
    val ipAddress: StateFlow<IpAddress> = _ipAddress

    init {
        viewModelScope.launch(Dispatchers.IO) {
            settings.ipAddress.collect {
                _ipAddress.value = it
            }
        }
    }

    fun saveAddress(address: IpAddress) {
        viewModelScope.launch(Dispatchers.IO) {
            settings.setAddress(address)
        }
    }
}