package com.example.clientservertesttask.client.di

import com.example.clientservertesttask.client.model.client.Client
import com.example.clientservertesttask.client.model.client.KtorClient
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ClientModule {
    @Binds
    abstract fun bindClient(
        client: KtorClient
    ): Client
}