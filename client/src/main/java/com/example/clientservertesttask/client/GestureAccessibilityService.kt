package com.example.clientservertesttask.client

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.example.clientservertesttask.client.model.Client
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object GestureAccessibilityService: AccessibilityService() {
    private lateinit var client: Client
    var startTime: Long? = null
        private set
    var endTime: Long? = null
        private set

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event != null) {
            when (event.eventType) {
                AccessibilityEvent.TYPE_GESTURE_DETECTION_START -> {
                    startTime = event.eventTime
                }

                AccessibilityEvent.TYPE_GESTURE_DETECTION_END -> {
                    endTime = event.eventTime
                    if (startTime != null) {
                        CoroutineScope(Dispatchers.IO).launch {
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

    private const val TAG = "GestureAccessibilityService"
}