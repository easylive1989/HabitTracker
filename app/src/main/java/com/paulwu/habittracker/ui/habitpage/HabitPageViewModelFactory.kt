package com.paulwu.habittracker.ui.habitpage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HabitPageViewModelFactory(private val application: Application, private val id: Int) :
    ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HabitPageViewModel(application, id) as T
        }
    }
