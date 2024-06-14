package com.example.clientservertesttask.client.model.client

import com.example.common.BrowserOpenEvent
import com.example.common.Gesture
import com.example.common.GestureResult
import kotlinx.coroutines.flow.Flow

interface Client {
    val isActive: Flow<Boolean>
    val gesture: Flow<Gesture>

    suspend fun connect()

    suspend fun sendBrowserOpenEvent(browserOpenEvent: BrowserOpenEvent)

    suspend fun sendGestureResult(gestureResult: GestureResult)

    suspend fun disconnect()
}