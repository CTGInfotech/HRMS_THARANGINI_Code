package com.example.retrofitauthtoken.activities


import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitauthtoken.calendar.MonthFormat
import com.example.retrofitauthtoken.calendar.MonthYearPickerDialog
import com.example.retrofitauthtoken.calendar.MonthYearPickerDialogFragment
import com.example.retrofitauthtoken.databinding.ActivityMainBinding
import java.text.DateFormatSymbols
import java.util.*


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var currentYear = 0
    private var yearSelected = 0
    private var monthSelected = 0
    private var textView: TextView? = null
    private var shortMonthsCheckBox: CheckBox? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUI()
    }

    private fun setUI() {
        textView = findViewById(com.example.retrofitauthtoken.R.id.tv_result)
        val dateRangeCheckBox =
            findViewById<CheckBox>(com.example.retrofitauthtoken.R.id.cbDateRange)
        val CustomTitleCheckBox =
            findViewById<CheckBox>(com.example.retrofitauthtoken.R.id.cbCustomTitle)
        shortMonthsCheckBox = findViewById(com.example.retrofitauthtoken.R.id.cbShortMonth)
        findViewById<View>(com.example.retrofitauthtoken.R.id.button).setOnClickListener {
            displayMonthYearPickerDialogFragment(
                dateRangeCheckBox.isChecked,
                CustomTitleCheckBox.isChecked
            )
        }
        val calendar = Calendar.getInstance()
        currentYear = calendar[Calendar.YEAR]
        yearSelected = currentYear
        monthSelected = calendar[Calendar.MONTH]
        updateViews()
    }

    private fun createDialog(customTitle: Boolean): MonthYearPickerDialogFragment {
        return MonthYearPickerDialogFragment.getInstance(
            monthSelected,
            yearSelected,
            if (customTitle) getString(com.example.retrofitauthtoken.R.string.app_name).toUpperCase(
                Locale.getDefault()
            ) else null,
            if (shortMonthsCheckBox!!.isChecked) MonthFormat.SHORT else MonthFormat.LONG
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

        return MonthYearPickerDialogFragment
            .getInstance(
                monthSelected,
                yearSelected,
                minDate,
                maxDate,
                if (customTitle) getString(com.example.retrofitauthtoken.R.string.app_name).uppercase(
                    Locale.getDefault()
                ) else null,
                if (shortMonthsCheckBox!!.isChecked) MonthFormat.SHORT else MonthFormat.LONG
            )
    }

    private fun displayMonthYearPickerDialogFragment(
        withRanges: Boolean,
        customTitle: Boolean
    ) {
        val dialogFragment=
            if (withRanges) createDialogWithRanges(customTitle) else createDialog(customTitle)
        dialogFragment.setOnDateSetListener(object : MonthYearPickerDialog.OnDateSetListener {
            override fun onDateSet(year: Int, monthOfYear: Int) {
                monthSelected = monthOfYear
                yearSelected = year
                updateViews()
            }
        })
        dialogFragment.show(supportFragmentManager, null)
    }

    private fun updateViews() {
        val month = DateFormatSymbols().months[monthSelected]
        textView!!.text = String.format("%s / %s", month, yearSelected)
    }
}