package com.paulwu.habittracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Duration
import java.time.Instant


@Entity
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "start_time") val startTime: Instant,
    @ColumnInfo(name = "frequency") val frequency: Duration,
    @ColumnInfo(name = "target") val targetTimes: Int
)
