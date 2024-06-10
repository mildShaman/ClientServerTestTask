package com.example.clientservertesttask.server.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GestureResultsDao {
    @Insert(GestureResultsEntity::class)
    suspend fun add(gestureResult: GestureResultsEntity)

    @Query("SELECT * FROM gesture_results")
    fun getAll(): Flow<List<GestureResultsEntity>>
}