package com.example.clientservertesttask.server.model.settings

import com.example.common.IpAddress
import kotlinx.coroutines.flow.Flow

interface AppSettings {
    val ipAddress: Flow<IpAddress>

    suspend fun setAddress(address: IpAddress)
}