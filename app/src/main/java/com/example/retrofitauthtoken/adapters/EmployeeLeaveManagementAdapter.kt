package com.example.retrofitauthtoken.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitauthtoken.R
import com.example.retrofitauthtoken.activities.AddLeaveActivity
import com.example.retrofitauthtoken.databinding.EmployeeLeaveListBinding
import com.example.retrofitauthtoken.fragments.EmployeeLeaveFragment
import java.text.SimpleDateFormat
import java.util.*

class EmployeeLeaveManagementAdapter(
    var context: Context
) : RecyclerView.Adapter<EmployeeLeaveManagementAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: EmployeeLeaveListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): EmployeeLeaveManagementAdapter.ViewHolder {
        return ViewHolder(
            EmployeeLeaveListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val list = EmployeeLeaveFragment.leavesList[position]
        holder.binding.leaveDescription.text = "Description: " + list.description
        holder.binding.noOfDays.text = "No of Days: " + list.duration
//        holder.binding.leaveDate.text = "Leave Date: " + list.leaveDate
        holder.binding.leaveType.text = "Leave Type: "+list.leavesType

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val date = list.leaveDate?.let { dateFormat.parse(it) }
        val cal = Calendar.getInstance()
        cal.time = date
        val month = SimpleDateFormat("MMM")
        val dateOf = SimpleDateFormat("dd")
        val year = SimpleDateFormat("yyyy")
        val monthName = month.format(cal.time)
        val yearName = year.format(cal.time)
        val dateName = dateOf.format(cal.time)
        holder.binding.leaveDate.text =
           "Leave Date: "+ monthName + " " + dateName + "," + yearName

        holder.binding.leaveManagementMore.setOnClickListener {
            val popup = PopupMenu(holder.itemView.context, it)
            popup.menuInflater.inflate(R.menu.all_menu_options, popup.menu)
            val showEdit = popup.menu.findItem(R.id.edit)
            showEdit.isVisible = true
            val showCancel = popup.menu.findItem(R.id.cancel)
            showCancel.isVisible = true
            popup.setOnMenuItemClickListener { item ->
                when(item.itemId){
                    R.id.edit ->{
                        val intent = Intent(context,AddLeaveActivity::class.java)
                        intent.putExtra("position",position)
                        intent.putExtra("isEdit",true)
                        context.startActivity(intent)
                    }

                }
//                handleMenuOptions(item, holder)
                return@setOnMenuItemClickListener true
            }
            popup.show()
        }




    }

    override fun getItemCount(): Int {
        return EmployeeLeaveFragment.leavesList.size
    }


    private fun handleMenuOptions(
        item: MenuItem,
        holder: ViewHolder
    ) {
        when (item.itemId) {
            R.id.edit -> {
                val intent = Intent(context, AddLeaveActivity::class.java)

                context.startActivity(intent)
            }
            R.id.delete -> {
            }

        }
    }

}