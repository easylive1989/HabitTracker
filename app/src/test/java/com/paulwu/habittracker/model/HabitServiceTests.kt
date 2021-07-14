//package com.paulwu.habittracker.model
//
//import com.paulwu.habittracker.dao.CompleteRecordDao
//import com.paulwu.habittracker.dao.HabitDao
//import io.mockk.every
//import io.mockk.mockk
//import junit.framework.Assert.assertEquals
//import org.junit.Before
//import org.junit.Test
//import java.time.Duration
//import java.util.*
//
//class HabitServiceTests {
//    private lateinit var habitService: HabitService
//    private val startTime: Date = Calendar.getInstance().time;
//    private lateinit var completeTime: Date
//
//    @Before
//    fun setup() {
//        val calendar = Calendar.getInstance()
//        calendar.set(2021, 2, 3)
//        completeTime = calendar.time;
//        val mockHabitDao = mockk<HabitDao>(relaxed = true)
//        every { mockHabitDao.getAll() } returns listOf()
//        val mockCompleteRecordDao = mockk<CompleteRecordDao>(relaxed = true)
//        habitService = HabitService(mockHabitDao, mockCompleteRecordDao)
//    }
//
//    @Test
//    fun add_habit() {
//        givenHabit(Habit(123, "habit a", startTime, Duration.ofHours(2), 10))
//
//        assertEquals(
//            listOf(Habit(123, "habit a", startTime, Duration.ofHours(2), 10)),
//            habitService.getHabitList()
//        )
//    }
//
//    @Test
//    fun remove_habit() {
//        givenHabit(Habit(123, "habit a", startTime, Duration.ofHours(2), 10))
//
//        habitService.remove(123)
//
//        assertEquals(listOf<Habit>(), habitService.getHabitList())
//    }
//
//    @Test
//    fun complete_habit() {
//        givenHabit(Habit(123, "habit a", startTime, Duration.ofHours(2), 10))
//
//        habitService.complete(123);
//
//        assertEquals(
//            1,
//            habitService.getCompleteTimes(123)
//        )
//    }
//
//    private fun givenHabit(habit: Habit) {
//        habitService.add(habit)
//    }
//}