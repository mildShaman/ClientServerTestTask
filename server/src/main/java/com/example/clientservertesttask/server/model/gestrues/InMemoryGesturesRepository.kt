package com.example.clientservertesttask.server.model.gestrues

import com.example.common.Gesture
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryGesturesRepository @Inject constructor(): GesturesRepository {
    override fun getGestures(): List<Gesture> {
        return listOf(
            Gesture(50f, 50f, 200f, 50f, 500),
            Gesture(50f, 50f, 50f, 200f, 600),
        )
    }
}