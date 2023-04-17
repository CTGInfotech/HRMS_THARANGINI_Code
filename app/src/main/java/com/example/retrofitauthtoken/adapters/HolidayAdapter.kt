package com.example.retrofitauthtoken.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitauthtoken.activities.HolidayActivity
import com.example.retrofitauthtoken.databinding.HolidaysListBinding
import java.text.SimpleDateFormat
import java.util.*


class HolidayAdapter(
    var context: Context
) : RecyclerView.Adapter<HolidayAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: HolidaysListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HolidayAdapter.ViewHolder {
        return ViewHolder(
            HolidaysListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val list = HolidayActivity.holidayList[position]

        holder.binding.holidayName.text = list.name


        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val date = dateFormat.parse(list.startDate)
        val cal = Calendar.getInstance()
        cal.time = date
        val month = SimpleDateFormat("MMM")
        val dateOf = SimpleDateFormat("dd")
        val year = SimpleDateFormat("yyyy")
        val monthName = month.format(cal.time)
        val yearName = year.format(cal.time)
        val dateName = dateOf.format(cal.time)
        holder.binding.holidayDate.text =
            monthName + " " + dateName + "," + yearName


    }

    override fun getItemCount(): Int {
        return HolidayActivity.holidayList.size
    }


}