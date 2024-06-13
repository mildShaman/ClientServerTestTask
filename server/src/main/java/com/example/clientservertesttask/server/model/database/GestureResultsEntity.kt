package com.example.clientservertesttask.server.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.common.Gesture
import com.example.common.GestureResult

@Entity(tableName = "gesture_results")
data class GestureResultsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "start_x") val startX: Float,
    @ColumnInfo(name = "start_y") val startY: Float,
    @ColumnInfo(name = "end_x") val endX: Float,
    @ColumnInfo(name = "end_y") val endY: Float,
    val duration: Long,
    @ColumnInfo(name = "start_time") val startTime: Long,
    @ColumnInfo(name = "end_time") val endTime: Long
) {
    fun toGestureResult() = GestureResult(
        gestureData = Gesture(
            startX, startY, endX, endY, duration
        ),
        startTime, endTime
    )

    companion object {
        fun fromGestureResult(result: GestureResult) = GestureResultsEntity(
            id = 0,
            startX = result.gestureData.startX,
            startY = result.gestureData.startY,
            endX = result.gestureData.endX,
            endY = result.gestureData.endY,
            duration = result.gestureData.duration,
            startTime = result.startTime,
            endTime = result.endTime
        )
    }
}
