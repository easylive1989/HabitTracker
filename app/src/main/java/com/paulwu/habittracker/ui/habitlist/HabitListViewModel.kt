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

    private var habitListData: MutableList<Habit> = mutableListOf()
    private var completeRecordListData: MutableList<CompleteRecord> = mutableListOf()

    val displayHabitList: MutableLiveData<List<DisplayHabit>> by lazy {
        MutableLiveData()
    }

    init {
        completeRecordDao.getAll().observeForever { completeRecordList ->
            completeRecordListData = completeRecordList.toMutableList()
        }
        habitDao.getAll().observeForever { habitList ->
            habitListData = habitList.toMutableList()
            displayHabitList.value = getDisplayHabitList()
        }
    }


    private fun getDisplayHabitList(): List<DisplayHabit> {
        return habitListData.map { habit ->
            val completeTimes = completeRecordListData.count { it.habitId == habit.id }
            val dateNow = Instant.now()
            val canComplete = if (completeTimes == 0)
                habit.startTime.plus(habit.frequency).isBefore(dateNow) else
                completeRecordListData.last { it.habitId == habit.id }.completeTime.plus(habit.frequency)
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