package com.example.clientservertesttask.server.model.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.common.IpAddress
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreAppSettings @Inject constructor(
    @ApplicationContext private val context: Context
) : AppSettings {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

    override val ipAddress: Flow<IpAddress>
        get() {
            return context.dataStore.data.map { preferences ->
                IpAddress(
                    host = preferences[HOST_KEY] ?: IpAddress.DEFAULT.host,
                    port = preferences[PORT_KEY] ?: IpAddress.DEFAULT.port
                )
            }
        }

    override suspend fun setAddress(address: IpAddress) {
        context.dataStore.edit {
            it[HOST_KEY] = address.host
            it[PORT_KEY] = address.port
        }
    }

    companion object {
        const val PREFERENCES_NAME = "settings"

        val HOST_KEY = stringPreferencesKey("host")
        val PORT_KEY = intPreferencesKey("port")
    }
}