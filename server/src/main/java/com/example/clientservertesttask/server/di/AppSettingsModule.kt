package com.example.clientservertesttask.server.di

import com.example.clientservertesttask.server.model.settings.AppSettings
import com.example.clientservertesttask.server.model.settings.DataStoreAppSettings
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppSettingsModule {
    @Binds
    abstract fun bindAppSettings(
        appSettings: DataStoreAppSettings
    ): AppSettings
}