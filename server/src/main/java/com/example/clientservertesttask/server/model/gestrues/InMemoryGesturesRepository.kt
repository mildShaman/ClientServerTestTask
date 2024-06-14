package com.example.clientservertesttask.server.model.gestrues

import com.example.common.Gesture
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryGesturesRepository @Inject constructor(): GesturesRepository {
    override fun getGestures(): List<Gesture> {
        return listOf(
            Gesture(50f, 50f, 200f, 50f, 500),
            Gesture(50f, 50f, 50f, 1000f, 600),
            Gesture(400f, 1200f, 400f, 100f, 500),
            Gesture(400f, 1800f, 400f, 100f, 500),
            Gesture(400f, 1800f, 400f, 100f, 500),
            Gesture(400f, 400f, 400f, 1800f, 100)
        )
    }
}