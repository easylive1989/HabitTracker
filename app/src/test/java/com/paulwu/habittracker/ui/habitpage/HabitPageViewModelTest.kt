package com.paulwu.habittracker.ui.habitpage

import com.paulwu.habittracker.MainCoroutineRule
import com.paulwu.habittracker.dao.CompleteRecordDao
import com.paulwu.habittracker.dao.HabitDao
import com.paulwu.habittracker.model.CompleteRecord
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import java.time.Instant

@ExperimentalCoroutinesApi
class HabitPageViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `add complete times when user click complete button`() {
        mainCoroutineRule.runBlockingTest {
            val habitDao = mockk<HabitDao>(relaxed = true)
            val completeRecordDao = mockk<CompleteRecordDao>(relaxed = true)
            val habitPageViewModel = HabitPageViewModel(habitDao, completeRecordDao)

            val instant = Instant.now()
            habitPageViewModel.complete(1, instant)

            verify { completeRecordDao.insert(CompleteRecord(0, 1, instant)) }
        }
    }
}