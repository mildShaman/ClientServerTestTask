package com.example.common

import kotlinx.serialization.Serializable

@Serializable
data class BrowserOpenMessage(
    val time: Long,
    val isOpen: Boolean
)
