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
    private var isClientActive = false

    private var gesture: Gesture? = null

    override fun onServiceConnected() {
        super.onServiceConnected()

        scope.launch {
            client.isActive.collect {
                isClientActive = it
            }
        }
        scope.launch {
            client.receiveGesture {
                gesture = it
                mainScope.launch {
                    super.dispatchGesture(
                        createGestureDescription(it), null, null
                    )
                }
            }
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
        val path = Path()
        path.moveTo(gesture.startX, gesture.startY)
        path.lineTo(gesture.endX, gesture.endY)
        return path
    }

    private var startTime: Long? = null
    private var endTime: Long? = null

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event != null) {
            when (event.eventType) {
                AccessibilityEvent.TYPE_GESTURE_DETECTION_START -> {
                    startTime = event.eventTime
                }
                AccessibilityEvent.TYPE_GESTURE_DETECTION_END -> {
                    endTime = event.eventTime
                    if (gesture != null && startTime != null && isClientActive) {
                        scope.launch {
                            client.sendGestureResult(
                                GestureResult(gesture!!, startTime!!, endTime!!)
                            )
                        }
                    } else {
                        Log.w(TAG, "end time received, but cannot send response")
                    }
                }
                else -> { }
            }
        }
    }

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