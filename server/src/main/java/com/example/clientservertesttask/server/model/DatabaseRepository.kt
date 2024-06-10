package com.example.clientservertesttask.server.model

import com.example.common.GestureResult
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun addGestureResult(data: GestureResult)

    fun getAllGestureResults(): Flow<List<GestureResult>>
}