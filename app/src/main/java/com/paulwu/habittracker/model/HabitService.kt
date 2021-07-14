//package com.paulwu.habittracker.model
//
//import com.paulwu.habittracker.dao.CompleteRecordDao
//import com.paulwu.habittracker.dao.HabitDao
//import java.time.Instant
//
//class HabitService(
//    private val habitDao: HabitDao,
//    private val completeRecordDao: CompleteRecordDao
//) {
//    private val habitList: MutableList<Habit> = mutableListOf()
//    private val completeRecordList: MutableList<CompleteRecord> = mutableListOf()
//
//    init {
//        habitList.addAll(habitDao.getAll())
//        completeRecordList.addAll(completeRecordDao.getAll())
//    }
//
//    fun add(habit: Habit) {
//        habitList.add(habit)
//        habitDao.insert(habit)
//    }
//
//    fun getHabitList(): List<Habit> {
//        return habitList
//    }
//
//    fun getDisplayHabitList(): List<DisplayHabit> {
//        return habitList.map { habit ->
//            val completeTimes = completeRecordList.count { it.habitId == habit.id }
//            val dateNow = Instant.now()
//            val canComplete = if (completeTimes == 0)
//                habit.startTime.plus(habit.frequency).isBefore(dateNow) else
//                completeRecordList.last { it.habitId == habit.id }.completeTime.plus(habit.frequency).isBefore(dateNow)
//            DisplayHabit(
//                habit.id,
//                habit.name,
//                completeTimes,
//                habit.targetTimes,
//                canComplete,
//            )
//        }
//    }
//
//    fun remove(id: Int) {
//        val habitToRemove = habitList.first { habit -> habit.id == id }
//        habitList.removeIf { habit -> habit.id == id }
//        habitDao.remove(habitToRemove)
//    }
//
//    fun complete(id: Int) {
//        val completeRecord = CompleteRecord(0, id, Instant.now())
//        completeRecordList.add(completeRecord)
//        completeRecordDao.insert(completeRecord)
//    }
//
//    fun getCompleteTimes(id: Int): Int {
//        return completeRecordList.count { completeRecord -> completeRecord.habitId == id }
//    }
//
//    fun getDisplayHabit(id: Int): DisplayHabit {
//        return getDisplayHabitList().first { it.id == id }
//    }
//}