package com.example.clientservertesttask.server.di

import android.content.Context
import androidx.room.Room
import com.example.clientservertesttask.server.model.database.GestureResultsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): GestureResultsDatabase {
        return Room.databaseBuilder(context, GestureResultsDatabase::class.java, "gesture_result_database")
            .fallbackToDestructiveMigration()
            .build()
    }
}