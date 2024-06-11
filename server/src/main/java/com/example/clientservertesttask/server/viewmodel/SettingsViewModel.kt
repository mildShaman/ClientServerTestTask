package com.example.clientservertesttask.server.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientservertesttask.server.model.settings.AppSettings
import com.example.common.IpAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settings: AppSettings
): ViewModel() {
    private val _ipAddress = MutableStateFlow<IpAddress>(IpAddress.DEFAULT)
    val ipAddress: StateFlow<IpAddress> = _ipAddress

    init {
        viewModelScope.launch(Dispatchers.IO) {
            settings.getHost().combine(settings.getPort()) { host, port ->
                _ipAddress.value = IpAddress(host, port)
            }
        }
    }

    fun saveAddress(address: IpAddress) {
        viewModelScope.launch(Dispatchers.IO) {
            settings.setHost(address.host)
            settings.setPort(address.port)
        }
    }
}