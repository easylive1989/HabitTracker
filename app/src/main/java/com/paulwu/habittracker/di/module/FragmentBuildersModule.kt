package com.paulwu.habittracker.di.module

import com.paulwu.habittracker.ui.habitcreate.HabitCreateFragment
import com.paulwu.habittracker.ui.habitlist.HabitListFragment
import com.paulwu.habittracker.ui.habitpage.HabitPageFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeHabitListFragment(): HabitListFragment

    @ContributesAndroidInjector
    abstract fun contributeHabitCreateFragment(): HabitCreateFragment

    @ContributesAndroidInjector
    abstract fun contributeHabitPageFragment(): HabitPageFragment
}