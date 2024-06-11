package com.example.clientservertesttask.server.model.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreAppSettings @Inject constructor(
    @ApplicationContext private val context: Context
) : AppSettings {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

    override fun getHost(): Flow<String> {
        return context.dataStore.data.map {
            it[HOST_KEY] ?: DEFAULT_HOST
        }
    }

    override fun getPort(): Flow<Int> {
        return context.dataStore.data.map {
            it[PORT_KEY] ?: DEFAULT_PORT
        }
    }

    override suspend fun setHost(host: String) {
        context.dataStore.edit {
            it[HOST_KEY] = host
        }
    }

    override suspend fun setPort(port: Int) {
        context.dataStore.edit {
            it[PORT_KEY] = port
        }
    }

    companion object {
        const val PREFERENCES_NAME = "settings"

        val HOST_KEY = stringPreferencesKey("host")
        val PORT_KEY = intPreferencesKey("port")

        const val DEFAULT_HOST = "0.0.0.0"
        const val DEFAULT_PORT = 8080
    }
}