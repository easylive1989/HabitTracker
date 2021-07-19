package com.paulwu.habittracker.ui.habitpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.paulwu.habittracker.R
import com.paulwu.habittracker.databinding.FragmentHabitPageBinding
import com.paulwu.habittracker.model.DisplayHabit

class HabitPageFragment(private val habit: DisplayHabit) : Fragment() {

    private val viewModel: HabitPageViewModel by viewModels {
        HabitPageViewModelFactory(requireActivity().application, habit.id)
    }

    private lateinit var binding: FragmentHabitPageBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHabitInfo(habit)

        viewModel.displayHabit.observe(viewLifecycleOwner) { habit ->
            setHabitInfo(habit)
        }

        binding.buttonComplete.setOnClickListener {
            viewModel.complete(habit.id)
        }
    }

    private fun setHabitInfo(habit: DisplayHabit) {
        binding.titleHabit.text = habit.name
        binding.completeCount.text =
            getString(R.string.text_habit_page_complete_desc, habit.completeTimes)
        binding.targetCount.text = getString(
            R.string.text_habit_page_target_desc,
            (habit.targetTimes - habit.completeTimes)
        )
        binding.buttonComplete.visibility =
            if (!habit.isComplete() && habit.canComplete) View.VISIBLE else View.INVISIBLE
    }
}