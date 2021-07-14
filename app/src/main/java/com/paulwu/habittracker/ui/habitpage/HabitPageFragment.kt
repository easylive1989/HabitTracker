package com.paulwu.habittracker.ui.habitpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.paulwu.habittracker.R
import com.paulwu.habittracker.model.DisplayHabit

class HabitPageFragment(private val habit: DisplayHabit) : Fragment() {

    private val viewModel: HabitPageViewModel by viewModels {
        HabitPageViewModelFactory(requireActivity().application, habit.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habit, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHabitInfo(view, habit)

        viewModel.displayHabit.observe(viewLifecycleOwner) { habit ->
            setHabitInfo(view, habit)
        }

        view.findViewById<Button>(R.id.button_complete).setOnClickListener { _ ->
            viewModel.complete(habit.id)
        }
    }

    private fun setHabitInfo(view: View, habit: DisplayHabit) {
        view.findViewById<TextView>(R.id.title_habit).text = habit.name
        view.findViewById<TextView>(R.id.complete_count).text =
            getString(R.string.text_habit_page_complete_desc, habit.completeTimes)
        view.findViewById<TextView>(R.id.target_count).text = getString(
            R.string.text_habit_page_target_desc,
            (habit.targetTimes - habit.completeTimes)
        )
        view.findViewById<Button>(R.id.button_complete).visibility =
            if (!habit.isComplete() && habit.canComplete) View.VISIBLE else View.INVISIBLE
    }
}