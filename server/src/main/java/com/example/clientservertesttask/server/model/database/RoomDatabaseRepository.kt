package com.example.clientservertesttask.server.model.database

import com.example.clientservertesttask.server.model.DatabaseRepository
import com.example.common.GestureResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomDatabaseRepository(
    database: GestureResultsDatabase
): DatabaseRepository {
    private val dao = database.getDao()

    override suspend fun addGestureResult(data: GestureResult) {
        dao.add(GestureResultsEntity.fromGestureResult(data))
    }

    override fun getAllGestureResults(): Flow<List<GestureResult>> {
        return dao.getAll().map { list ->
            list.map { it.toGestureResult() }
        }
    }
}