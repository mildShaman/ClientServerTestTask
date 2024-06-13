package com.example.clientservertesttask.client.model

import com.example.common.Gesture
import com.example.common.GestureResult
import kotlinx.coroutines.flow.Flow

interface Client {
    val isActive: Flow<Boolean>

    suspend fun connect()

    suspend fun receiveGesture(onReceive: (Gesture) -> Unit)

    suspend fun sendGestureResult(gestureResult: GestureResult)

    suspend fun disconnect()
}