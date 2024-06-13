package com.example.clientservertesttask.client

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.example.clientservertesttask.client.model.Client
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

object GestureAccessibilityService: AccessibilityService() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private lateinit var client: Client

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
                    if (startTime != null) {
                        scope.launch {
                            client.sendGestureTime(startTime!!, endTime!!)
                        }
                    } else {
                        Log.w(TAG, "received end time, but not start time")
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