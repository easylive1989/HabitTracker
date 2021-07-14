package com.paulwu.habittracker.utils

import androidx.room.TypeConverter
import java.time.Duration
import java.time.Instant

class DatabaseTypeConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun toDate(value: Long): Instant {
            return Instant.ofEpochMilli(value)
        }

        @TypeConverter
        @JvmStatic
        fun fromDate(instant: Instant): Long {
            return instant.toEpochMilli()
        }

        @TypeConverter
        @JvmStatic
        fun toDuration(value: Long): Duration {
            return Duration.ofMinutes(value)
        }

        @TypeConverter
        @JvmStatic
        fun fromDuration(duration: Duration): Long {
            return duration.toMinutes()
        }
    }
}