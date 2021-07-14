package com.paulwu.habittracker.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.paulwu.habittracker.model.CompleteRecord


@Dao
interface CompleteRecordDao {
    @Query("SELECT * FROM CompleteRecord")
    fun getAll(): LiveData<List<CompleteRecord>>

    @Insert
    fun insert(completeRecord: CompleteRecord)

    @Delete
    fun remove(completeRecord: CompleteRecord)
}
