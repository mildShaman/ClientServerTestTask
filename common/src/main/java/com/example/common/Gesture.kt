package com.example.common

import kotlinx.serialization.Serializable

@Serializable
data class Gesture(
    val startX: Float,
    val startY: Float,
    val endX: Float,
    val endY: Float,
    val duration: Long
)