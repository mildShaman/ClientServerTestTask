package com.example.clientservertesttask.client.di

import com.example.clientservertesttask.client.model.settings.AppSettings
import com.example.clientservertesttask.client.model.settings.DataStoreAppSettings
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