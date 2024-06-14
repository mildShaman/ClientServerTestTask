package com.example.clientservertesttask.client.model.client

import android.util.Log
import com.example.clientservertesttask.client.model.settings.AppSettings
import com.example.common.BrowserOpenEvent
import com.example.common.Gesture
import com.example.common.GestureResult
import com.example.common.IpAddress
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.websocket.close
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KtorClient @Inject constructor(
    private val settings: AppSettings
): Client {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private val client = HttpClient(CIO) {
        install(WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(Json)
        }
    }

    private var ipAddress = IpAddress.DEFAULT

    init {
        scope.launch {
            settings.ipAddress.collect {
                ipAddress = it
            }
        }
    }

    private lateinit var session: DefaultClientWebSocketSession

    private val _isActive = MutableStateFlow(false)
    override val isActive: Flow<Boolean>
        get() = _isActive

    private val _gesture = MutableSharedFlow<Gesture>()
    override val gesture: Flow<Gesture>
        get() = _gesture

    private fun initSession(ipAddress: IpAddress) {
        session = runBlocking {
            client.webSocketSession(
                host = ipAddress.host,
                port = ipAddress.port,
                path = "/"
            )
        }
        _isActive.value = session.isActive
        scope.launch {
            while (session.isActive) {
                val gesture = session.receiveDeserialized<Gesture>()
                Log.d(TAG, "$gesture received")
                _gesture.emit(gesture)
            }
        }
    }

    override suspend fun connect() {
        initSession(ipAddress)
    }

    override suspend fun sendBrowserOpenEvent(browserOpenEvent: BrowserOpenEvent) {
        if (_isActive.value) {
            session.sendSerialized(browserOpenEvent)
            Log.d(TAG, "$browserOpenEvent sent")
        }
    }

    override suspend fun sendGestureResult(gestureResult: GestureResult) {
        session.sendSerialized(gestureResult)
        Log.d(TAG, "$gestureResult sent")
    }

    override suspend fun disconnect() {
        _isActive.value = false
        job.cancel()
        session.close()
    }

    companion object {
        const val TAG = "KtorClient"
    }
}