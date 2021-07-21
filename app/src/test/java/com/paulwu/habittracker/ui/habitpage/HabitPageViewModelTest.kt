package com.paulwu.habittracker.ui.habitpage

import com.paulwu.habittracker.MainCoroutineRule
import com.paulwu.habittracker.dao.CompleteRecordDao
import com.paulwu.habittracker.dao.HabitDao
import com.paulwu.habittracker.model.CompleteRecord
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Instant

@ExperimentalCoroutinesApi
class HabitPageViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var habitDao : HabitDao
    private lateinit var completeRecordDao : CompleteRecordDao
    private lateinit var habitPageViewModel : HabitPageViewModel

    @Before
    fun setUp() {
        habitDao = mockk(relaxed = true)
        completeRecordDao = mockk(relaxed = true)
        habitPageViewModel = HabitPageViewModel(habitDao, completeRecordDao)
    }


    @Test
    fun `add complete times when user click complete button`() {
        mainCoroutineRule.runBlockingTest {
            val instant = Instant.now()
            habitPageViewModel.complete(1, instant)

            dbShouldInsert(CompleteRecord(0, 1, instant))
        }
    }

    private fun dbShouldInsert(completeRecord: CompleteRecord) {
        verify { completeRecordDao.insert(completeRecord) }
    }
}