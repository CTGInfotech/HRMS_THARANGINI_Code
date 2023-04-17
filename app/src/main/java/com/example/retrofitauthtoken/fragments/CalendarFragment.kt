package com.example.retrofitauthtoken.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitauthtoken.adapters.CalendarAdapter
import com.example.retrofitauthtoken.databinding.FragmentCalendarBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)

class CalendarFragment : Fragment(), CalendarAdapter.OnItemListener {

    private val binding by lazy { FragmentCalendarBinding.inflate(layoutInflater) }
    private var monthYearText: TextView? = null
    private var calendarRecyclerView: RecyclerView? = null
    private var selectedDate: LocalDate? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initWidgets()
        selectedDate = LocalDate.now()
        setMonthView()

        binding.previousBtn.setOnClickListener {
            previousMonthAction()
        }
        binding.nextBtn.setOnClickListener {
            nextMonthAction()
        }
       return binding.root
    }
    private fun initWidgets() {
        calendarRecyclerView = binding.calendarRecyclerView
//            findViewById(R.id.calendarRecyclerView)
        monthYearText = binding.monthYearTV
//            findViewById(R.id.monthYearTV)
    }

    private fun setMonthView() {
        monthYearText!!.text = monthYearFromDate(selectedDate)
        val daysInMonth = daysInMonthArray(selectedDate)
        val calendarAdapter = CalendarAdapter(daysInMonth,requireContext(),this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 7)
        calendarRecyclerView!!.layoutManager = layoutManager
        calendarRecyclerView!!.adapter = calendarAdapter
    }
    private fun daysInMonthArray(date: LocalDate?): ArrayList<String> {
        val daysInMonthArray = ArrayList<String>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate!!.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }
        return daysInMonthArray
    }

    private fun monthYearFromDate(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date!!.format(formatter)
    }
    fun previousMonthAction() {
        selectedDate = selectedDate!!.minusMonths(1)
        setMonthView()
    }

    fun nextMonthAction() {
        selectedDate = selectedDate!!.plusMonths(1)
        setMonthView()
    }

    override fun onItemClick(position: Int, dayText: String?) {
        if (dayText != "") {
            val message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate)
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
    }

}