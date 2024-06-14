package com.example.clientservertesttask.server.model.server

import android.util.Log
import com.example.clientservertesttask.server.model.DatabaseRepository
import com.example.clientservertesttask.server.model.gestrues.GesturesRepository
import com.example.clientservertesttask.server.model.settings.AppSettings
import com.example.common.BrowserOpenEvent
import com.example.common.GestureResult
import com.example.common.IpAddress
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.server.application.install
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSocketServerSession
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.receiveDeserialized
import io.ktor.server.websocket.sendSerialized
import io.ktor.server.websocket.webSocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.util.ArrayList
import java.util.Collections
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KtorServer @Inject constructor(
    private val settings: AppSettings,
    private val databaseRepository: DatabaseRepository,
    gesturesRepository: GesturesRepository
): Server {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private val ipAddress = MutableStateFlow(IpAddress.DEFAULT)
    private val _isStarted = MutableStateFlow(false)
    private val gestures = gesturesRepository.getGestures()

    init {
        scope.launch {
            settings.ipAddress.collect {
                ipAddress.value = it
            }
        }
    }

    private var server = initServer(ipAddress.value.host, ipAddress.value.port)

    private fun initServer(host: String, port: Int): ApplicationEngine {
        return embeddedServer(
            factory = Netty,
            host = host,
            port = port
        ) {
            install(WebSockets) {
                contentConverter = KotlinxWebsocketSerializationConverter(Json)
            }

            routing {
                val sessions = Collections.synchronizedList<WebSocketServerSession>(ArrayList())

                webSocket("/") {
                    sessions.add(this)

                    for (session in sessions) {
                        val browserOpenEvent = receiveDeserialized<BrowserOpenEvent>()
                        Log.d("KtorServer", "$browserOpenEvent received")

                        if (browserOpenEvent.isOpen) {
                            for (gesture in gestures) {
                                sendSerialized(gesture)
                                Log.d("KtorServer", "$gesture sent")
                                val gestureResult = receiveDeserialized<GestureResult>()
                                databaseRepository.addGestureResult(gestureResult)
                            }
                        }
                    }
                }
            }
        }
    }

    override val isStarted: Flow<Boolean>
        get() = _isStarted

    override suspend fun start() {
        server = initServer(ipAddress.value.host, ipAddress.value.port)
        CoroutineScope(Dispatchers.IO).launch {
            server.start(wait = true)
        }
        _isStarted.value = true
    }

    override suspend fun stop() {
        if (_isStarted.value) {
            try {
                job.cancel()
                server.stop()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        _isStarted.value = false
    }
}