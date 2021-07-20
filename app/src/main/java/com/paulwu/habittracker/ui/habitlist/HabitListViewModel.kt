package com.paulwu.habittracker.ui.habitlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulwu.habittracker.dao.CompleteRecordDao
import com.paulwu.habittracker.dao.HabitDao
import com.paulwu.habittracker.model.CompleteRecord
import com.paulwu.habittracker.model.DisplayHabit
import com.paulwu.habittracker.model.Habit
import java.time.Instant
import javax.inject.Inject

class HabitListViewModel @Inject constructor(
    habitDao: HabitDao,
    completeRecordDao: CompleteRecordDao,
) : ViewModel() {

    private var habitList: MutableList<Habit> = mutableListOf()
    private var completeRecordList: MutableList<CompleteRecord> = mutableListOf()

    val displayHabitList: MutableLiveData<List<DisplayHabit>> by lazy {
        MutableLiveData()
    }

    init {
        completeRecordDao.getAll().observeForever { completeRecordList ->
            this.completeRecordList = completeRecordList.toMutableList()
        }
        habitDao.getAll().observeForever { habitList ->
            this.habitList = habitList.toMutableList()
            displayHabitList.value = getDisplayHabitList()
        }
    }


    private fun getDisplayHabitList(): List<DisplayHabit> {
        return habitList.map { habit ->
            val completeTimes = completeRecordList.count { it.habitId == habit.id }
            val dateNow = Instant.now()
            val canComplete = if (completeTimes == 0)
                habit.startTime.plus(habit.frequency).isBefore(dateNow) else
                completeRecordList.last { it.habitId == habit.id }.completeTime.plus(habit.frequency)
                    .isBefore(dateNow)
            DisplayHabit(
                habit.id,
                habit.name,
                completeTimes,
                habit.targetTimes,
                canComplete,
            )
        }
    }
}