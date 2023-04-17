package com.example.retrofitauthtoken.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitauthtoken.R
import com.example.retrofitauthtoken.activities.EmployeeFillTimesheetActivity
import com.example.retrofitauthtoken.databinding.EmployeeTimesheetDaysListBinding
import com.example.retrofitauthtoken.databinding.TimesheetTableViewBinding
import com.example.retrofitauthtoken.fragments.EmployeeClientFragment.Companion.list

@RequiresApi(Build.VERSION_CODES.N)

class EmployeeTimesheetAdapter(
    var context: Context
) : RecyclerView.Adapter<EmployeeTimesheetAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: TimesheetTableViewBinding) :
        RecyclerView.ViewHolder(binding.root)

companion object{
     fun setHeaderBg(view: View) {
        view.setBackgroundResource(R.drawable.table_header_cell_bg)
    }

     fun setContentBg(view: View) {
        view.setBackgroundResource(R.drawable.table_content_cell_bg)
    }

}
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): EmployeeTimesheetAdapter.ViewHolder {
        return ViewHolder(
            TimesheetTableViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )



    }

//    private fun setHeaderBg(view: View) {
//        view.setBackgroundResource(R.drawable.table_header_cell_bg)
//    }
//
//    private fun setContentBg(view: View) {
//        view.setBackgroundResource(R.drawable.table_content_cell_bg)
//    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val rowPos = holder.adapterPosition
        holder.setIsRecyclable(false)
        if (rowPos == 0) {


            // Header Cells. Main Headings appear here
            holder.apply {
                setHeaderBg(binding.timesheetDate)
                setHeaderBg(binding.timesheetDay)
                setHeaderBg(binding.timesheetAwayHrs)
                setHeaderBg(binding.timesheetWorkHrs)
                setHeaderBg(binding.timesheetDescription)

                binding.timesheetDate.text = "Date"
                binding.timesheetDay.text = "Day"

                binding.timesheetWorkHrs.setText("Work Hrs")
                binding.timesheetWorkHrs.isEnabled = false
                binding.timesheetWorkHrs.textColors
                binding.timesheetAwayHrs.setText("Away Hrs")
                binding.timesheetAwayHrs.isEnabled = false
                binding.timesheetDescription.setText("Description")
                binding.timesheetDescription.isEnabled = false
            }
        } else {
            val list = EmployeeFillTimesheetActivity.daysList[rowPos - 1]

            holder.apply {
                setContentBg(binding.timesheetDate)
                setContentBg(binding.timesheetDay)
                setContentBg(binding.timesheetWorkHrs)
                setContentBg(binding.timesheetAwayHrs)
                setContentBg(binding.timesheetDescription)

                holder.binding.timesheetDay.text =  list.day
        holder.binding.timesheetDate.text = list.date
        holder.binding.timesheetAwayHrs.setText(list.awayHrs).toString()
        holder.binding.timesheetWorkHrs.setText(list.workHrs).toString()
        holder.binding.timesheetDescription.setText(list.description)

                if (list.day.equals("Sunday") || list.day.equals("Saturday")){
                    holder.binding.timesheetDay.setBackgroundColor(Color.LTGRAY)
                    holder.binding.timesheetDescription.setBackgroundColor(Color.LTGRAY)
                    holder.binding.timesheetAwayHrs.setBackgroundColor(Color.LTGRAY)
                    holder.binding.timesheetWorkHrs.setBackgroundColor(Color.LTGRAY)
                    holder.binding.timesheetDate.setBackgroundColor(Color.LTGRAY)
                    holder.binding.timesheetDescription.isEnabled = false
                    holder.binding.timesheetAwayHrs.isEnabled = false
                    holder.binding.timesheetWorkHrs.isEnabled = false
                }
            }
        }




    }

    override fun getItemCount(): Int {
        return EmployeeFillTimesheetActivity.daysList.size+1
    }


}