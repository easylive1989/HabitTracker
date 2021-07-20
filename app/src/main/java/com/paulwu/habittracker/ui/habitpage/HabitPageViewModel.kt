package com.paulwu.habittracker.ui.habitpage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulwu.habittracker.dao.CompleteRecordDao
import com.paulwu.habittracker.dao.HabitDao
import com.paulwu.habittracker.model.CompleteRecord
import com.paulwu.habittracker.model.DisplayHabit
import com.paulwu.habittracker.model.Habit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import javax.inject.Inject

class HabitPageViewModel @Inject constructor(
    private val habitDao: HabitDao,
    private val completeRecordDao: CompleteRecordDao
) : ViewModel() {
    private var completeRecordListData: MutableList<CompleteRecord> = mutableListOf()

    private lateinit var habit: Habit

    val displayHabit: MutableLiveData<DisplayHabit> by lazy {
        MutableLiveData()
    }


    fun init(habitId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                habit = habitDao.get(habitId)
            }

            completeRecordDao.getAll().observeForever { completeRecordList ->
                completeRecordListData = completeRecordList.toMutableList()
                val completeTimes = completeRecordListData.count { it.habitId == habit.id }
                val dateNow = Instant.now()
                val canComplete = if (completeTimes == 0)
                    habit.startTime.plus(habit.frequency).isBefore(dateNow) else
                    completeRecordListData.last { it.habitId == habit.id }.completeTime.plus(habit.frequency)
                        .isBefore(dateNow)

                displayHabit.value?.completeTimes = completeTimes
                displayHabit.value?.canComplete = canComplete
                displayHabit.value = DisplayHabit(
                    habit.id,
                    habit.name,
                    completeTimes,
                    habit.targetTimes,
                    canComplete,
                )
            }
        }

    }

    fun complete(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val completeRecord = CompleteRecord(0, id, Instant.now())
                completeRecordDao.insert(completeRecord)
            }
        }
    }
}