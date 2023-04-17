package com.example.retrofitauthtoken.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitauthtoken.R
import com.example.retrofitauthtoken.databinding.EmployeeProjectListBinding
import com.example.retrofitauthtoken.fragments.EmployeeProjectFragment

class EmployeeProjectAdapter(
    var context: Context
) : RecyclerView.Adapter<EmployeeProjectAdapter.ViewHolder>() {

    private var inflater: LayoutInflater = (LayoutInflater.from(context))


    inner class ViewHolder(val binding: EmployeeProjectListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): EmployeeProjectAdapter.ViewHolder {
        return ViewHolder(
            EmployeeProjectListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val list = EmployeeProjectFragment.projectList[position]
        holder.binding.projectCode.text = "Project Code: " + list.projectCode
        holder.binding.projectName.text = "Project Name: " + list.projectName
        holder.binding.projectClientName.text = "Client Name: " + list.client?.clientName

        holder.binding.projectViewBtn.setOnClickListener{

            val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
                .create()

            val view = inflater.inflate(R.layout.custom_project_dialog, null)
            val title = view.findViewById<TextView>(R.id.top_header_title)
            title.text = "View project"
            val arrow = view.findViewById<ImageView>(R.id.items_arrow_back)
            arrow.setOnClickListener {
                builder.dismiss()
            }

            val projectCode = view.findViewById<TextView>(R.id.view_project_code)
            val projectName = view.findViewById<TextView>(R.id.view_project_name)
            val proStartDate = view.findViewById<TextView>(R.id.view_project_start_date)
            val proEndDate = view.findViewById<TextView>(R.id.view_project_end_date)
            val proClientName = view.findViewById<TextView>(R.id.view_project_client)

            setDataToView(
                projectCode,
                projectName,
                proStartDate,
                proEndDate,
                proClientName,
                position
            )
            builder.setView(view)
//            builder.dismiss()

            builder.setCanceledOnTouchOutside(true)
            builder.show()
        }

    }

    override fun getItemCount(): Int {
        return EmployeeProjectFragment.projectList.size
    }

    private fun setDataToView(
        proCode: TextView, proName: TextView, proStartDate: TextView,
        proEndDate: TextView, proClientName: TextView, dataPosition: Int
    ) {
        proCode.text = EmployeeProjectFragment.projectList.get(dataPosition).projectCode
        proName.text = EmployeeProjectFragment.projectList.get(dataPosition).projectName
        proStartDate.text = EmployeeProjectFragment.projectList.get(dataPosition).startDate
        proEndDate.text = EmployeeProjectFragment.projectList.get(dataPosition).endDate
        proClientName.text =
            EmployeeProjectFragment.projectList.get(dataPosition).client?.clientName


    }

}