package com.paulwu.habittracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity
data class CompleteRecord (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "habit_id") val habitId: Int,
    @ColumnInfo(name = "complete_time") var completeTime: Instant
)