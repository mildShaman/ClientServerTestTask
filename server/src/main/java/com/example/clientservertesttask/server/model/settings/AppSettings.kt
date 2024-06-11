package com.example.clientservertesttask.server.model.settings

import kotlinx.coroutines.flow.Flow

interface AppSettings {
    fun getHost(): Flow<String>

    fun getPort(): Flow<Int>

    suspend fun setHost(host: String)

    suspend fun setPort(port: Int)
}