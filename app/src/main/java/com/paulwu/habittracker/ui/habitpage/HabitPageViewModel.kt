package com.paulwu.habittracker.ui.habitpage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.paulwu.habittracker.dao.AppDatabase
import com.paulwu.habittracker.dao.CompleteRecordDao
import com.paulwu.habittracker.dao.HabitDao
import com.paulwu.habittracker.model.CompleteRecord
import com.paulwu.habittracker.model.DisplayHabit
import com.paulwu.habittracker.model.Habit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant

class HabitPageViewModel(application: Application, private val id: Int) :
    AndroidViewModel(application) {
    private lateinit var completeRecordDao: CompleteRecordDao

    private lateinit var habitDao: HabitDao
    private var completeRecordListData: MutableList<CompleteRecord> = mutableListOf()

    private lateinit var habit: Habit

    val displayHabit: MutableLiveData<DisplayHabit> by lazy {
        MutableLiveData()
    }

    init {
        initDatabase(application)
    }

    private fun initDatabase(application: Application) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val database = Room.databaseBuilder(
                    application.applicationContext,
                    AppDatabase::class.java, "habit"
                ).build()
                habitDao = database.habitDao()
                completeRecordDao = database.completeRecordDao()
                habit = habitDao.get(id)
            }

            completeRecordDao.getAll().observeForever {
                completeRecordList ->
                completeRecordListData = completeRecordList.toMutableList()
                val completeTimes = completeRecordListData.count { it.habitId == habit.id }
                val dateNow = Instant.now()
                val canComplete = if (completeTimes == 0)
                    habit.startTime.plus(habit.frequency).isBefore(dateNow) else
                    completeRecordListData.last { it.habitId == habit.id }.completeTime.plus(habit.frequency).isBefore(dateNow)

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