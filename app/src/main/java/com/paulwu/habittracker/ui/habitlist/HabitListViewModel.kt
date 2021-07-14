package com.paulwu.habittracker.ui.habitlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.paulwu.habittracker.dao.CompleteRecordDao
import com.paulwu.habittracker.dao.HabitDao
import com.paulwu.habittracker.model.CompleteRecord
import com.paulwu.habittracker.model.DisplayHabit
import com.paulwu.habittracker.model.Habit
import java.time.Instant
import javax.inject.Inject

class HabitListViewModel @Inject constructor(
    private val habitDao: HabitDao,
    private val completeRecordDao: CompleteRecordDao,
    application: Application
) : AndroidViewModel(application) {

    private lateinit var habitLiveData: LiveData<List<Habit>>
    private lateinit var completeRecordLiveData: LiveData<List<CompleteRecord>>
    private var habitListData: MutableList<Habit> = mutableListOf()
    private var completeRecordListData: MutableList<CompleteRecord> = mutableListOf()

    private val habitObserver = Observer<List<Habit>> { habitList ->
        habitListData = habitList.toMutableList()
        displayHabitList.value = getDisplayHabitList()
    }

    private val completeRecordObserver = Observer<List<CompleteRecord>> { completeRecordList ->
        completeRecordListData = completeRecordList.toMutableList()
        displayHabitList.value = getDisplayHabitList()
    }

    val displayHabitList: MutableLiveData<List<DisplayHabit>> by lazy {
        MutableLiveData()
    }

    init {
        initDatabase(application)
    }

    private fun initDatabase(application: Application) {
//        viewModelScope.launch {
            habitLiveData = habitDao.getAll()
            completeRecordLiveData = completeRecordDao.getAll()

            habitLiveData.observeForever(habitObserver)
            completeRecordLiveData.observeForever(completeRecordObserver)

//        }
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

    override fun onCleared() {
        super.onCleared()
        habitLiveData.removeObserver(habitObserver)
        completeRecordLiveData.removeObserver(completeRecordObserver)
    }
}