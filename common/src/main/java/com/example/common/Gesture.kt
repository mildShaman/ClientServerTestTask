package com.example.common

import kotlinx.serialization.Serializable

@Serializable
data class Gesture(
    val startX: Int,
    val startY: Int,
    val distanceX: Float,
    val distanceY: Float
)