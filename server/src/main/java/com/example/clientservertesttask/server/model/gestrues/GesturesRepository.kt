package com.example.clientservertesttask.server.model.gestrues

import com.example.common.Gesture

interface GesturesRepository {
    fun getGestures(): List<Gesture>
}