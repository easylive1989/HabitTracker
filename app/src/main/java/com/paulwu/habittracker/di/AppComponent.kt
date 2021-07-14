package com.paulwu.habittracker.di

import android.app.Application
import com.paulwu.habittracker.HabitTrackerApplication
import com.paulwu.habittracker.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DatabaseModule::class,
        FragmentBuildersModule::class,
        AndroidInjectionModule::class,
        MainActivityModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(habitTrackerApplication: HabitTrackerApplication)
}