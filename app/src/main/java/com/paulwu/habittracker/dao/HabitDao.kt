package com.paulwu.habittracker.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.paulwu.habittracker.model.Habit

@Dao
interface HabitDao {
    @Query("SELECT * FROM Habit")
    fun getAll() : LiveData<List<Habit>>

    @Query("SELECT * FROM Habit WHERE id = :id")
    fun get(id: Int) : Habit

    @Insert
    fun insert(habit: Habit)

    @Delete
    fun remove(habit: Habit)
}