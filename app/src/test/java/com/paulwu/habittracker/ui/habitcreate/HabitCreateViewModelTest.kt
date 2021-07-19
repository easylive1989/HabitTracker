package com.paulwu.habittracker.ui.habitcreate

import com.paulwu.habittracker.MainCoroutineRule
import com.paulwu.habittracker.dao.HabitDao
import com.paulwu.habittracker.model.Habit
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

@ExperimentalCoroutinesApi
class HabitCreateViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `add habit to habit list`() {
        mainCoroutineRule.runBlockingTest {
            val habitDao = mockk<HabitDao>(relaxed = true)

            val viewModel  = HabitCreateViewModel(habitDao)

            viewModel.habitSetting.name = "habit"
            viewModel.habitSetting.date = LocalDate.of(2021, 1, 1)
            viewModel.habitSetting.time = LocalTime.of(0,0,0)
            viewModel.habitSetting.frequency = Duration.ofMinutes(2)
            viewModel.habitSetting.targetCount = 20

            viewModel.add(viewModel.habitSetting)

            verify { habitDao.insert(Habit(0, "habit", viewModel.habitSetting.startTime, Duration.ofMinutes(2), 20)) }
        }
    }
}