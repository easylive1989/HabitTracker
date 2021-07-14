package com.paulwu.habittracker.model

data class DisplayHabit(
    val id: Int,
    val name: String,
    var completeTimes: Int,
    val targetTimes: Int,
    var canComplete: Boolean
) {
    fun isComplete() : Boolean {
        return targetTimes <= completeTimes
    }
}
