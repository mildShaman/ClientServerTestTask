package com.example.clientservertesttask.server.model.server

interface Server {
    suspend fun start()

    suspend fun stop()
}