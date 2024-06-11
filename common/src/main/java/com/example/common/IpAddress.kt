package com.example.common

data class IpAddress(
    val host: String,
    val port: Int
) {
    companion object {
        val DEFAULT = IpAddress(
            host = "0.0.0.0",
            port = 8080
        )
    }
}
