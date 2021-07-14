package com.paulwu.habittracker.di.module

import android.content.Context
import androidx.room.Room
import com.paulwu.habittracker.dao.AppDatabase
import com.paulwu.habittracker.dao.CompleteRecordDao
import com.paulwu.habittracker.dao.HabitDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "habit"
        ).build()
    }

    @Singleton
    @Provides
    fun provideHabitDao(database: AppDatabase): HabitDao {
        return database.habitDao()
    }

    @Singleton
    @Provides
    fun provideCompleteRecordDao(database: AppDatabase): CompleteRecordDao {
        return database.completeRecordDao()
    }
}