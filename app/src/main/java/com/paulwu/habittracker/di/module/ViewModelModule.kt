package com.paulwu.habittracker.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paulwu.habittracker.ViewModelFactory
import com.paulwu.habittracker.di.key.ViewModelKey
import com.paulwu.habittracker.ui.habitcreate.HabitCreateViewModel
import com.paulwu.habittracker.ui.habitlist.HabitListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @Binds
    @ViewModelKey(HabitListViewModel::class)
    abstract fun bindHomeFragmentViewModel(viewModel: HabitListViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(HabitCreateViewModel::class)
    abstract fun bindForecastFragmentViewModel(viewModel: HabitCreateViewModel): ViewModel

}