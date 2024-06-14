package com.example.clientservertesttask.client

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import androidx.compose.ui.platform.AndroidUiDispatcher
import com.example.clientservertesttask.client.model.client.Client
import com.example.common.Gesture
import com.example.common.GestureResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GestureAccessibilityService: AccessibilityService() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private val mainScope = CoroutineScope(AndroidUiDispatcher.Main + job)

    @Inject
    lateinit var client: Client

    override fun onServiceConnected() {
        scope.launch {
            client.gesture.collect { gesture ->
                performGesture(gesture)
            }
        }
    }

    private suspend fun performGesture(gesture: Gesture) {
        mainScope.launch {
            val startTime = System.currentTimeMillis()
            super.dispatchGesture(
                createGestureDescription(gesture),
                object: GestureResultCallback() {
                    override fun onCompleted(gestureDescription: GestureDescription?) {
                        val endTime = System.currentTimeMillis()
                        scope.launch {
                            client.sendGestureResult(
                                GestureResult(gesture, startTime, endTime)
                            )
                        }
                    }
                },
                null
            )
            Log.d(TAG, "$gesture dispatched")
        }
    }

    private fun createGestureDescription(gesture: Gesture): GestureDescription {
        return GestureDescription.Builder()
            .addStroke(
                GestureDescription.StrokeDescription(
                    createPath(gesture),0, gesture.duration
                )
            )
            .build()
    }

    private fun createPath(gesture: Gesture): Path {
        return Path().apply {
            moveTo(gesture.startX, gesture.startY)
            lineTo(gesture.endX, gesture.endY)
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) { }

    override fun onInterrupt() {
        Log.w(TAG, "service interrupted")
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    companion object {
        const val TAG = "GestureAccessibilityService"
    }
}