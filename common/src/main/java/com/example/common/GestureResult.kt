package com.example.common

import kotlinx.serialization.Serializable

@Serializable
data class GestureResult(
    val gestureData: Gesture,
    val startTime: Long,
    val endTime: Long
)