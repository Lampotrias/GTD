package com.lampotrias.gtd.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.applandeo.materialcalendarview.CalendarDay
import com.lampotrias.gtd.R
import com.lampotrias.gtd.databinding.FragmentCalendarBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    @Suppress("unused")
    private val viewModel: CalendarViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)

        return binding.root
    }

//    https://github.com/Applandeo/Material-Calendar-View

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val calendarDays: MutableList<CalendarDay> = ArrayList()

        val calendar: Calendar = Calendar.getInstance()
        val calendarDay = CalendarDay(calendar)
//        calendarDay.selectedBackgroundDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_launcher_background)
//        calendarDay.backgroundDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_launcher_background)
        calendarDay.imageResource = R.drawable.ic_someday
        calendarDays.add(calendarDay)
        binding.calendarView.setDate(calendar)

        binding.calendarView.setCalendarDays(calendarDays)

        binding.calendarView
    }
}
