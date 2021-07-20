package com.paulwu.habittracker.ui.habitpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.paulwu.habittracker.R
import com.paulwu.habittracker.databinding.FragmentHabitPageBinding
import com.paulwu.habittracker.di.Injectable
import com.paulwu.habittracker.model.DisplayHabit
import javax.inject.Inject

class HabitPageFragment() : Fragment(), Injectable {
    companion object {
        const val ARG_HABIT_ID = "habitid"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: HabitPageViewModel by viewModels { viewModelFactory }

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
//        setHabitInfo(habit)
        arguments?.takeIf { it.containsKey(ARG_HABIT_ID) }?.apply {
            val habitId = getInt(ARG_HABIT_ID)
            viewModel.init(habitId)

            viewModel.displayHabit.observe(viewLifecycleOwner) { habit ->
                setHabitInfo(habit)
            }

            binding.buttonComplete.setOnClickListener {
                viewModel.complete(habitId)
            }
        }

//        viewModel.displayHabit.observe(viewLifecycleOwner) { habit ->
//            setHabitInfo(habit)
//        }
//
//        binding.buttonComplete.setOnClickListener {
//            viewModel.complete(habit.id)
//        }
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