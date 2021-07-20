package com.paulwu.habittracker.ui.habitcreate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.paulwu.habittracker.R
import com.paulwu.habittracker.databinding.FragmentHabitCreateBinding
import com.paulwu.habittracker.di.Injectable
import com.paulwu.habittracker.widget.DatePickerFragment
import com.paulwu.habittracker.widget.NotCompleteDialogFragment
import com.paulwu.habittracker.widget.TimePickerFragment
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HabitCreateFragment : Fragment(), Injectable {
    companion object {
        const val TAG_DATE_PICKER = "date_picker"
        const val TAG_TIME_PICKER = "time_picker"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: HabitCreateViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentHabitCreateBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStartDate(LocalDate.now())
        binding.datePicker.setOnClickListener {
            DatePickerFragment { _, year, month, day ->
                setStartDate(LocalDate.of(year, month, day))
            }.show(childFragmentManager, TAG_DATE_PICKER)
        }

        setStartTime(LocalTime.now().withSecond(0).withNano(0))
        binding.timePicker.setOnClickListener {
            TimePickerFragment { _, hour, minute ->
                setStartTime(LocalTime.of(hour, minute))
            }.show(childFragmentManager, TAG_TIME_PICKER)
        }

        initPicker(binding.daysPicker, 0, 31)
        initPicker(binding.hoursPicker, 0, 23)
        initPicker(binding.minutesPicker, 0, 59)

        binding.buttonAdd.setOnClickListener { _ ->
            viewModel.habitSetting.name = binding.editTextHabitName.text.toString()
            viewModel.habitSetting.frequency =
                Duration.ofMinutes(0).plusMinutes(binding.minutesPicker.value.toLong())
                    .plusHours(binding.hoursPicker.value.toLong())
                    .plusDays(binding.daysPicker.value.toLong())
            viewModel.habitSetting.targetCount = binding.targetCount.text.toString().toInt()

            if (!viewModel.habitSetting.isAllFieldsComplete()) {
                NotCompleteDialogFragment().show(childFragmentManager, "")
            } else {
                viewModel.add(viewModel.habitSetting)
                view.findNavController()
                    .navigate(R.id.action_HabitCreateFragment_to_HabitListFragment)
            }

        }
    }

    private fun setStartTime(localTime: LocalTime) {
        viewModel.habitSetting.time = localTime
        binding.timePicker.text = localTime.format(DateTimeFormatter.ISO_LOCAL_TIME).substring(0, 5)
    }

    private fun setStartDate(localDate: LocalDate) {
        viewModel.habitSetting.date = localDate
        binding.datePicker.text = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    private fun initPicker(numberPicker: NumberPicker, min: Int, max: Int) {
        numberPicker.displayedValues = (min..max).map { num -> num.toString() }.toTypedArray()
        numberPicker.minValue = min
        numberPicker.maxValue = max
    }
}