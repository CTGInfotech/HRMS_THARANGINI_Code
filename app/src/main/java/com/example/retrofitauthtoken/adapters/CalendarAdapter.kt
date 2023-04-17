package com.example.retrofitauthtoken.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitauthtoken.R

class CalendarAdapter(
    private val daysOfMonth: ArrayList<String>,
     var context: Context,
    private val onItemListener: OnItemListener
) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>(){
    private var inflater: LayoutInflater = (LayoutInflater.from(context))

    class ViewHolder internal constructor(itemView: View, onItemListener: CalendarAdapter.OnItemListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val dayOfMonth: TextView
        val cellEvent: TextView
        private val onItemListener: CalendarAdapter.OnItemListener
        override fun onClick(view: View) {
            onItemListener.onItemClick(adapterPosition, dayOfMonth.text as String)
        }

        init {
            dayOfMonth = itemView.findViewById(R.id.cellDayText)
            cellEvent = itemView.findViewById(R.id.cell_event_name)
            this.onItemListener = onItemListener
            itemView.setOnClickListener(this)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.166666666).toInt()
        return ViewHolder(view, onItemListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dayOfMonth.setText(daysOfMonth[position])


    }

    override fun getItemCount(): Int {
        return daysOfMonth.size

    }

    interface OnItemListener {
        fun onItemClick(position: Int, dayText: String?)
    }

}

