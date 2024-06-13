package com.example.common

import kotlinx.serialization.Serializable

@Serializable
data class BrowserOpenEvent(
    val time: Long,
    val isOpen: Boolean
)
