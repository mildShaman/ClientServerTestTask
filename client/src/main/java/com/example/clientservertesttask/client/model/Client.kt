package com.example.clientservertesttask.client.model

import android.gesture.Gesture
import kotlinx.coroutines.flow.Flow

interface Client {
    val gesture: Flow<Gesture>

    suspend fun sendGestureTime(start: Long, end: Long)
}