package com.example.clientservertesttask.client.model

import com.example.common.Gesture
import com.example.common.GestureResult
import kotlinx.coroutines.flow.Flow

interface Client {
    val gesture: Flow<Gesture>

    suspend fun sendGestureResult(gestureResult: GestureResult)
}