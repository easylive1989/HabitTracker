package com.paulwu.habittracker.ui

import android.app.Application
import com.paulwu.habittracker.MainCoroutineRule
import com.paulwu.habittracker.dao.HabitDao
import com.paulwu.habittracker.model.Habit
import com.paulwu.habittracker.ui.habitcreate.HabitCreateViewModel
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime


class HabitCreateViewModelTest {
    @ExperimentalCoroutinesApi
    @Before
    fun setupDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
    // WITH
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun add_habit() {
        val habitDao = mockk<HabitDao>(relaxed = true)
        val mockApplication = mockk<Application>(relaxed = true)

        val viewModel  = HabitCreateViewModel(habitDao, mockApplication)

        viewModel.habitSetting.name = "habit"
        viewModel.habitSetting.date = LocalDate.of(2021, 1, 1)
        viewModel.habitSetting.time = LocalTime.of(0,0,0)
        viewModel.habitSetting.frequency = Duration.ofMinutes(2)
        viewModel.habitSetting.targetCount = 20

        viewModel.add(viewModel.habitSetting)

        verify { habitDao.insert(Habit(0, "habit", viewModel.habitSetting.startTime, Duration.ofMinutes(2), 20)) }
    }
}