package com.example.retrofitauthtoken.activities

import android.annotation.SuppressLint
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitauthtoken.adapters.EmployeeTimesheetAdapter
import com.example.retrofitauthtoken.calendar.MonthFormat
import com.example.retrofitauthtoken.calendar.MonthYearPickerDialog
import com.example.retrofitauthtoken.calendar.MonthYearPickerDialogFragment
import com.example.retrofitauthtoken.databinding.ActivityMain2Binding
import com.example.retrofitauthtoken.models.Timesheet
import java.text.DateFormatSymbols
import java.util.*

class MainActivity2 : AppCompatActivity() {
    private val binding by lazy { ActivityMain2Binding.inflate(layoutInflater) }
    private var currentYear = 0
    private var yearSelected = 0
    private var monthSelected = 0
    private val adapter by lazy { EmployeeTimesheetAdapter(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUI()

        binding.empAddTimesheetRv.adapter = adapter
//        binding.empAddTimesheetRv.addItemDecoration(
//            DividerItemDecoration(
//                applicationContext,
//                DividerItemDecoration.VERTICAL
//            )
//        )
    }

    private fun setUI() {
        val calendar = Calendar.getInstance()
        currentYear = calendar[Calendar.YEAR]
        yearSelected = currentYear
        monthSelected = calendar[Calendar.MONTH]
        binding.button.setOnClickListener {
            displayMonthYearPickerDialogFragment(false, true)

        }

    }

    private fun createDialog(): MonthYearPickerDialogFragment {
        return MonthYearPickerDialogFragment.getInstance(
            monthSelected,
            yearSelected,
            null,
            MonthFormat.LONG
        )

    }

    private fun createDialogWithRanges(customTitle: Boolean): MonthYearPickerDialogFragment {
        val minYear = 2010
        val maxYear = currentYear
        val maxMoth = 11
        val minMoth = 0
        val minDay = 1
        val maxDay = 31
        val minDate: Long
        val maxDate: Long
        val calendar = Calendar.getInstance()
        calendar.clear()
        calendar[minYear, minMoth] = minDay
        minDate = calendar.timeInMillis
        calendar.clear()
        calendar[maxYear, maxMoth] = maxDay
        maxDate = calendar.timeInMillis
        if (calendar.get(Calendar.DAY_OF_WEEK).equals(Calendar.SUNDAY)){
            Color.parseColor("#000000")
        }

        return MonthYearPickerDialogFragment
            .getInstance(
                monthSelected,
                yearSelected,
                minDate,
                maxDate,
                if (customTitle) getString(com.example.retrofitauthtoken.R.string.app_name).uppercase(
                    Locale.getDefault()
                ) else null,
                MonthFormat.LONG
            )
    }

    private fun displayMonthYearPickerDialogFragment(
        withRanges: Boolean,
        customTitle: Boolean
    ) {
        val dialogFragment =
            if (withRanges) createDialogWithRanges(customTitle) else createDialog()
        dialogFragment.setOnDateSetListener(object : MonthYearPickerDialog.OnDateSetListener {
            override fun onDateSet(year: Int, monthOfYear: Int) {
                monthSelected = monthOfYear
                yearSelected = year
                val month = DateFormatSymbols().months[monthSelected]
                binding.button.setText(String.format("%s / %s", month, yearSelected))

                printDatesInMonth(year, monthOfYear)


            }
        })
        dialogFragment.show(supportFragmentManager, null)
    }

//    private fun updateViews() {
//        val month = DateFormatSymbols().months[monthSelected]
//        binding.button.setText(String.format("%s / %s", month, yearSelected))
//
//    }

    @SuppressLint("SimpleDateFormat", "NotifyDataSetChanged")
    fun printDatesInMonth(year: Int, month: Int) {
        val fmt = SimpleDateFormat("dd-MMM")
        val cal: android.icu.util.Calendar = android.icu.util.Calendar.getInstance()
        cal.clear()
        cal.set(year, month, 1)
        val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
        val daysInMonth: Int = cal.getActualMaximum(android.icu.util.Calendar.DAY_OF_MONTH)



        for (i in 0 until daysInMonth) {

            EmployeeFillTimesheetActivity.daysList.add(
                Timesheet(
                    fmt.format(cal.time),
                    dayFormat.format(cal.time),
                    "8",
                    "0",
                    "gkfhj"
                )
            )

            cal.add(android.icu.util.Calendar.DAY_OF_MONTH, 1)

        }
        adapter.notifyDataSetChanged()
    }


}