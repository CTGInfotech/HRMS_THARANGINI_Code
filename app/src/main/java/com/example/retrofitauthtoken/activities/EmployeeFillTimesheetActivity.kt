package com.example.retrofitauthtoken.activities


import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitauthtoken.R
import com.example.retrofitauthtoken.adapters.EmployeeTimesheetAdapter
import com.example.retrofitauthtoken.calendar.MonthFormat
import com.example.retrofitauthtoken.calendar.MonthYearPickerDialogFragment
import com.example.retrofitauthtoken.databinding.ActivityEmployeeFillTimesheetBinding
import com.example.retrofitauthtoken.models.Timesheet
import com.example.retrofitauthtoken.models.TimesheetResponse
import com.example.retrofitauthtoken.network.ApiClient
import com.example.retrofitauthtoken.requests.TimesheetRequest
import com.example.retrofitauthtoken.response.ClientResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormatSymbols
import java.util.*

@SuppressLint("NotifyDataSetChanged")

class EmployeeFillTimesheetActivity : AppCompatActivity() {


    private val binding by lazy { ActivityEmployeeFillTimesheetBinding.inflate(layoutInflater) }
    private val adapter by lazy { EmployeeTimesheetAdapter(applicationContext) }
    private var currentYear = 0
    private var yearSelected = 0
    private var monthSelected = 0

    companion object {
        var daysList = ArrayList<Timesheet>()
        @SuppressLint("ConstantLocale")
        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        attachListeners()
        setUI()
    }

    @SuppressLint("SetTextI18n")
    private fun attachListeners() {
        binding.timesheetMonthPicker.setOnClickListener {
            val inflater: LayoutInflater = (LayoutInflater.from(this))
            val view = inflater.inflate(R.layout.dialog_date, null)
            val dialog = AlertDialog.Builder(this).setView(view)
                .setPositiveButton("Done") { _, _ ->
                    val title = view.findViewById<DatePicker>(R.id.date_picker)
                    binding.timesheetMonthPicker.setText(
                        title.dayOfMonth.toString() + "-" + (title.month + 1).toString() + "-" + title.year.toString()

                    )
                    printDatesInMonth(title.year, title.month)
                }
                .create()
            dialog.show()
        }
        binding.timesheetFillRv.adapter = adapter
        binding.timesheetFillRv.layoutManager = LinearLayoutManager(this)

        binding.addTsBtn.setOnClickListener {
            fillTimesheetApi()
        }
    }

    private fun setUI() {
        val calendar = Calendar.getInstance()
        currentYear = calendar[Calendar.YEAR]
        yearSelected = currentYear
        monthSelected = calendar[Calendar.MONTH]
        binding.timesheetMonthPicker.setOnClickListener {
            displayMonthYearPickerDialogFragment(false,false)
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
        val maxMoth = 12
        val minMoth = 1
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
                if (customTitle) getString(R.string.app_name).uppercase(
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
        dialogFragment.setOnDateSetListener { year, month ->
            monthSelected = month
            yearSelected = year
            val month = DateFormatSymbols().months[monthSelected]
            binding.timesheetMonthPicker.setText(
                String.format(
                    "%s - %s",
                    yearSelected,
                    monthSelected+1
                )
            )

            printDatesInMonth(yearSelected, monthSelected)
        }
        dialogFragment.show(supportFragmentManager, null)
    }


    @SuppressLint("SimpleDateFormat", "NotifyDataSetChanged")
    fun printDatesInMonth(year: Int, month: Int) {
        val fmt = SimpleDateFormat("dd-MMM")
        val cal: Calendar = Calendar.getInstance()
 daysList.clear()
        cal.set(year, month, 1)
//        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val daysInMonth: Int = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in 0 until daysInMonth) {
            daysList.add(
                Timesheet(
                    fmt.format(cal.time),
                    dayFormat.format(cal.time),
                    "0",
                    "0",
                    ""
                )
            )
            cal.add(Calendar.DAY_OF_MONTH, 1)
        }
        binding.timesheetFillRv.visibility = View.VISIBLE
        adapter.notifyDataSetChanged()
    }


    private fun fillTimesheetApi() {
        val timesheetClientName = binding.timesheetClientName.text.toString()
        val timeProjectName = binding.timesheetProjectName.text.toString()
        val calendarMonth = binding.timesheetMonthPicker.text.toString()

        val retIn = ApiClient.create(applicationContext)
        retIn.fillTimesheet(
            TimesheetRequest(
                clientName = timesheetClientName,
                projectName = timeProjectName,
                month = calendarMonth,
                timesheet = daysList
            )
        ).enqueue(object : Callback<TimesheetResponse> {

            override fun onFailure(call: Call<TimesheetResponse>, t: Throwable) {

                Toast.makeText(this@EmployeeFillTimesheetActivity, t.message, Toast.LENGTH_SHORT)
                    .show()
                adapter.notifyDataSetChanged()

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<TimesheetResponse>,
                response: Response<TimesheetResponse>
            ) {
                val fillTimesheetResponse = response.body()

                if (fillTimesheetResponse?.message != null) {

                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            fillTimesheetResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    if (fillTimesheetResponse.payload?.timesheet != null && fillTimesheetResponse.payload?.timesheet!!.isNotEmpty()) {

                        daysList = fillTimesheetResponse.payload!!.timesheet

                        runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                fillTimesheetResponse.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            adapter.notifyDataSetChanged()
                        }

                    } else {
                        runOnUiThread {
                            Toast.makeText(
                                applicationContext, fillTimesheetResponse.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            adapter.notifyDataSetChanged()
                        }

                    }
                }
                else{
                    try {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        Toast.makeText(applicationContext,jObjError.getString("message"),Toast.LENGTH_SHORT).show()

                    } catch (err: Exception) {
                        Toast.makeText(applicationContext, err.message, Toast.LENGTH_LONG).show();
                    }
                    runOnUiThread {
                        adapter.notifyDataSetChanged()

                    }
                }
//                else {
//                    runOnUiThread {
//                        Toast.makeText(
//                            applicationContext,
//                            response.message(),
//                            Toast.LENGTH_SHORT
//                        ).show()
//
//                        adapter.notifyDataSetChanged()
//                    }
//                }

            }


        })
    }
}