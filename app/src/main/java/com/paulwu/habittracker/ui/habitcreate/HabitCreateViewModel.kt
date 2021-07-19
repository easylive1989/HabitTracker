package com.paulwu.habittracker.ui.habitcreate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulwu.habittracker.dao.HabitDao
import com.paulwu.habittracker.model.Habit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HabitCreateViewModel @Inject constructor(private val habitDao: HabitDao): ViewModel() {
    val habitSetting: HabitSetting = HabitSetting.empty()

    fun add(habitSetting: HabitSetting) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                habitDao.insert(Habit(0, habitSetting.name, habitSetting.startTime, habitSetting.frequency, habitSetting.targetCount))
            }
        }
    }
}