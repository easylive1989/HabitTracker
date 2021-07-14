package com.paulwu.habittracker.di.module

import com.paulwu.habittracker.ui.habitcreate.HabitCreateFragment
import com.paulwu.habittracker.ui.habitlist.HabitListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeHabitListFragment(): HabitListFragment

    @ContributesAndroidInjector
    abstract fun contributeHabitCreateFragment(): HabitCreateFragment
}