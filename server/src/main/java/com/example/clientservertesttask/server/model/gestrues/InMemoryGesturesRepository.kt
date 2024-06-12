package com.example.clientservertesttask.server.model.gestrues

import com.example.common.Gesture
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryGesturesRepository @Inject constructor(): GesturesRepository {
    override fun getGestures(): List<Gesture> {
        return listOf(
            Gesture(50, 50, 0f, .5f),
            Gesture(50, 50, .5f, 0f),
        )
    }
}