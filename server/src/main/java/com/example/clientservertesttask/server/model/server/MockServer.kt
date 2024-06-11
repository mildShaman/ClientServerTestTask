package com.example.clientservertesttask.server.model.server

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockServer @Inject constructor(): Server {
    private val _isStarted = MutableStateFlow(false)

    override val isStarted: Flow<Boolean>
        get() = _isStarted

    override suspend fun start() {
        _isStarted.value = true
    }

    override suspend fun stop() {
        _isStarted.value = false
    }
}