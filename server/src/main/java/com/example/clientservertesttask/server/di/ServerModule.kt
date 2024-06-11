package com.example.clientservertesttask.server.di

import com.example.clientservertesttask.server.model.server.MockServer
import com.example.clientservertesttask.server.model.server.Server
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServerModule {
    @Binds
    abstract fun bindServer(
        server: MockServer
    ): Server
}