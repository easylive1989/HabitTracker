package com.paulwu.habittracker.ui.habitlist

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.paulwu.habittracker.model.DisplayHabit
import com.paulwu.habittracker.ui.habitpage.HabitPageFragment

class HabitPagerAdapter(fragmentManager: FragmentManager, lifeCycle: Lifecycle, private val habitList: List<DisplayHabit>) : FragmentStateAdapter(fragmentManager, lifeCycle) {
    override fun getItemCount(): Int {
        return habitList.size
    }

    override fun createFragment(position: Int): Fragment {
       return HabitPageFragment(habitList[position])
    }

}