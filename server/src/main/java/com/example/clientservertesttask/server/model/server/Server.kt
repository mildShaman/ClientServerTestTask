package com.example.clientservertesttask.server.model.server

import kotlinx.coroutines.flow.Flow

interface Server {
    val isStarted: Flow<Boolean>

    suspend fun start()

    suspend fun stop()
}