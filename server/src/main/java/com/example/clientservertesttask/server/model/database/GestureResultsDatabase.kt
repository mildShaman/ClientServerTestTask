package com.example.clientservertesttask.server.model.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [GestureResultsEntity::class],
    exportSchema = false
)
abstract class GestureResultsDatabase: RoomDatabase() {
    abstract fun getDao(): GestureResultsDao
}