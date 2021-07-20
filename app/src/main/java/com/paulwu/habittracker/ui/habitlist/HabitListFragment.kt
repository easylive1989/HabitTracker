package com.paulwu.habittracker.ui.habitlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.paulwu.habittracker.R
import com.paulwu.habittracker.databinding.FragmentHabitListBinding
import com.paulwu.habittracker.di.Injectable
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HabitListFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<HabitListViewModel> { viewModelFactory }

    private lateinit var binding: FragmentHabitListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener { fab ->
            fab.findNavController().navigate(R.id.action_HabitListFragment_to_HabitCreateFragment)
        }

        viewModel.displayHabitList.observe(viewLifecycleOwner) { habitList ->
            val habitPagerAdapter = HabitPagerAdapter(childFragmentManager, lifecycle, habitList)
            binding.viewPager.adapter = habitPagerAdapter
        }
    }
}

