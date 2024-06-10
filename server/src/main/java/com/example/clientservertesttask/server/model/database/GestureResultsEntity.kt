package com.example.clientservertesttask.server.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.common.Gesture
import com.example.common.GestureResult

@Entity(tableName = "gesture_results")
data class GestureResultsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "start_x") val startX: Int,
    @ColumnInfo(name = "start_y") val startY: Int,
    @ColumnInfo(name = "distance_x") val distanceX: Float,
    @ColumnInfo(name = "distance_y") val distanceY: Float,
    @ColumnInfo(name = "start_time") val startTime: Long,
    @ColumnInfo(name = "end_time") val endTime: Long
) {
    fun toGestureResult() = GestureResult(
        gestureData = Gesture(
            startX, startY, distanceX, distanceY
        ),
        startTime, endTime
    )

    companion object {
        fun fromGestureResult(result: GestureResult) = GestureResultsEntity(
            id = 0,
            startX = result.gestureData.startX,
            startY = result.gestureData.startY,
            distanceX = result.gestureData.distanceX,
            distanceY = result.gestureData.distanceY,
            startTime = result.startTime,
            endTime = result.endTime
        )
    }
}
