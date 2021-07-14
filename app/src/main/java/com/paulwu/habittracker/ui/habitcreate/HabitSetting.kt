package com.paulwu.habittracker.ui.habitcreate

import java.time.*

data class HabitSetting(
    var name: String,
    var date: LocalDate?,
    var time: LocalTime?,
    var frequency: Duration,
    var targetCount: Int
) {
    fun isAllFieldsComplete(): Boolean {
        return name.isNotEmpty() && date != null && time != null && !frequency.isZero && targetCount > 0
    }

    val startTime: Instant
        get() {
            val localDateTime = LocalDateTime.of(date, time)
            return localDateTime.atZone(ZoneId.systemDefault()).toInstant()
        }

    companion object {
        fun empty(): HabitSetting {
            return HabitSetting("", null, null, Duration.ofMinutes(0), 0)
        }
    }
}