package com.paulwu.habittracker.ui.habitlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.paulwu.habittracker.MainCoroutineRule
import com.paulwu.habittracker.dao.CompleteRecordDao
import com.paulwu.habittracker.dao.HabitDao
import com.paulwu.habittracker.model.DisplayHabit
import com.paulwu.habittracker.model.Habit
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Duration
import java.time.Instant

@ExperimentalCoroutinesApi
class HabitListViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var habitDao : HabitDao
    private lateinit var completeRecordDao : CompleteRecordDao
    private lateinit var viewModel: HabitListViewModel

    @Before
    fun setUp() {
        habitDao = mockk(relaxed = true)
        completeRecordDao = mockk(relaxed = true)
    }

    @Test
    fun `add habit to habit list`() {
        mainCoroutineRule.runBlockingTest {
            givenHabitList(
                listOf(
                    Habit(
                        1,
                        "habit 1",
                        Instant.parse("2021-05-01T00:00:00Z"),
                        Duration.ofMinutes(5),
                        1000
                    ),
                    Habit(
                        2,
                        "habit 2",
                        Instant.parse("2021-05-02T00:00:00Z"),
                        Duration.ofHours(5),
                        100
                    ),
                    Habit(
                        3,
                        "habit 3",
                        Instant.parse("2021-05-03T00:00:00Z"),
                        Duration.ofDays(5),
                        30
                    ),
                )
            )

            viewModel = HabitListViewModel(habitDao, completeRecordDao)

            habitListLiveDataShouldBe(
                listOf(
                    DisplayHabit(1, "habit 1", 0, 1000, true),
                    DisplayHabit(2, "habit 2", 0, 100, true),
                    DisplayHabit(3, "habit 3", 0, 30, true),
                )
            )
        }
    }

    private fun habitListLiveDataShouldBe(
        listOf: List<DisplayHabit>
    ) {
        assertEquals(
            listOf, viewModel.displayHabitList.value
        )
    }

    private fun givenHabitList(listOf: List<Habit>) {
        val habitList = MutableLiveData<List<Habit>>().apply {
            value = listOf
        }

        every { habitDao.getAll() } returns habitList
    }
}