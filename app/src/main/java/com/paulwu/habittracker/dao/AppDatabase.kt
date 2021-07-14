package com.paulwu.habittracker.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paulwu.habittracker.model.CompleteRecord
import com.paulwu.habittracker.model.Habit
import com.paulwu.habittracker.utils.DatabaseTypeConverter

@Database(entities = arrayOf(Habit::class, CompleteRecord::class), version = 1, exportSchema = true)
@TypeConverters(
    DatabaseTypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun completeRecordDao(): CompleteRecordDao
}