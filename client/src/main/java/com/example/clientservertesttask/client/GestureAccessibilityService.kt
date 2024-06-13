package com.example.clientservertesttask.client

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.example.clientservertesttask.client.model.Client
import com.example.common.Gesture
import com.example.common.GestureResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

object GestureAccessibilityService: AccessibilityService() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private val mainScope = CoroutineScope(Dispatchers.Main + job)

    private lateinit var client: Client
    private var gesture: Gesture? = null

    init {
        scope.launch {
            client.gesture.collect {
                gesture = it
                mainScope.launch {
                    super.dispatchGesture(
                        createGestureDescription(gesture!!), null, null
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
                    if (gesture != null && startTime != null) {
                        scope.launch {
                            client.sendGestureResult(
                                GestureResult(gesture!!, startTime!!, endTime!!)
                            )
                        }
                    } else {
                        Log.w(TAG, "end time received, but missing gesture data")
                    }
                }

                else -> { /*nothing to do*/ }
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

    private const val TAG = "GestureAccessibilityService"
}