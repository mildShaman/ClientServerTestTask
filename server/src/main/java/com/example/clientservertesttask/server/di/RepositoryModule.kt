package com.example.clientservertesttask.server.di

import com.example.clientservertesttask.server.model.DatabaseRepository
import com.example.clientservertesttask.server.model.database.RoomDatabaseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindDatabaseRepository(
        repository: RoomDatabaseRepository
    ): DatabaseRepository
}