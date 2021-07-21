package com.paulwu.habittracker.ui.habitcreate

import com.paulwu.habittracker.MainCoroutineRule
import com.paulwu.habittracker.dao.HabitDao
import com.paulwu.habittracker.model.Habit
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Duration
import java.time.Instant
import java.time.ZoneId

@ExperimentalCoroutinesApi
class HabitCreateViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var habitDao : HabitDao
    private lateinit var viewModel : HabitCreateViewModel

    @Before
    fun setUp() {
        habitDao = mockk(relaxed = true)
        viewModel = HabitCreateViewModel(habitDao)
    }

    @Test
    fun `add habit should call db to insert`() {
        mainCoroutineRule.runBlockingTest {
            val instant = Instant.parse("2021-01-01T00:00:00Z")
            viewModel.habitSetting.name = "habit"
            viewModel.habitSetting.date = instant.atZone(ZoneId.systemDefault()).toLocalDate()
            viewModel.habitSetting.time = instant.atZone(ZoneId.systemDefault()).toLocalTime()
            viewModel.habitSetting.frequency = Duration.ofMinutes(2)
            viewModel.habitSetting.targetCount = 20

            viewModel.add(viewModel.habitSetting)

            dbShouldInsert(
                Habit(
                    0,
                    "habit",
                    instant,
                    Duration.ofMinutes(2),
                    20
                )
            )
        }
    }

    private fun dbShouldInsert(habit: Habit) {
        verify {
            habitDao.insert(
                habit
            )
        }
    }
}