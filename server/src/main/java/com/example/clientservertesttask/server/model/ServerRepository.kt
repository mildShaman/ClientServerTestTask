package com.example.clientservertesttask.server.model

import com.example.common.Gesture

interface ServerRepository {
    suspend fun sendGesture(gesture: Gesture)
}