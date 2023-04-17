package com.example.retrofitauthtoken.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitauthtoken.R
import com.example.retrofitauthtoken.databinding.EmployeeClientListBinding
import com.example.retrofitauthtoken.fragments.EmployeeClientFragment
import com.example.retrofitauthtoken.models.FindClient
import java.util.*

class EmployeeClientAdapter(
    var context: Context
) : RecyclerView.Adapter<EmployeeClientAdapter.ViewHolder>() {
    private var inflater: LayoutInflater = (LayoutInflater.from(context))

    inner class ViewHolder(val binding: EmployeeClientListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): EmployeeClientAdapter.ViewHolder {


        return ViewHolder(
            EmployeeClientListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val list = EmployeeClientFragment.clientList[position]
        holder.binding.clientName.text = "Client Name: " + list.clientName
        holder.binding.clientId.text = "Client ID: " + list.clientId

        holder.binding.clientView.setOnClickListener {
            val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
                .create()

            val view = inflater.inflate(R.layout.client_custom_dialog, null)
            val title = view.findViewById<TextView>(R.id.top_header_title)
            title.text = "View client"
            val arrow = view.findViewById<ImageView>(R.id.items_arrow_back)
            arrow.setOnClickListener {

                builder.dismiss()
            }

            val clientId = view.findViewById<TextView>(R.id.view_client_id)
            val clientName = view.findViewById<TextView>(R.id.view_client_name)
            setDataToView(clientId,clientName, position)

            builder.setView(view)
//            builder.dismiss()

            builder.setCanceledOnTouchOutside(true)
            builder.show()
        }
    }
    override fun getItemCount(): Int {
        return EmployeeClientFragment.clientList.size
    }

    private fun setDataToView(clientId: TextView, clientName: TextView, dataPosition: Int) {
        clientId.setText(EmployeeClientFragment.clientList[dataPosition].clientId)
        clientName.text = EmployeeClientFragment.clientList[dataPosition].clientName


    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(clientList: ArrayList<FindClient>) {
        EmployeeClientFragment.clientList = clientList
        notifyDataSetChanged()
    }


//    fun setFilter(newsArrayList: ArrayList<FindClient>) {
//        EmployeeClientFragment.clientList.clear()
//        EmployeeClientFragment.clientList.addAll(newsArrayList)
//        notifyDataSetChanged()
//    }
}