package com.example.retrofitauthtoken.adapters

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitauthtoken.databinding.EmployeePayrollListBinding
import com.example.retrofitauthtoken.fragments.EmployeePayrollFragment


class EmployeePayrollAdapter(
    var context: Context
) : RecyclerView.Adapter<EmployeePayrollAdapter.ViewHolder>() {
    private var inflater: LayoutInflater = (LayoutInflater.from(context))
    private var referenceID: Long = 0

    inner class ViewHolder(val binding: EmployeePayrollListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): EmployeePayrollAdapter.ViewHolder {


        return ViewHolder(
            EmployeePayrollListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val list = EmployeePayrollFragment.payrollList[position]
        holder.binding.salaryMonth.text = "Salary Month: " + list.salaryMonth
        holder.binding.salaryYear.text = "Salary Year: " + list.salaryYear


        holder.binding.payrollView.setOnClickListener {
            val downloadManager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
            val uri: Uri =
                Uri.parse(list.paySlip)
            val request = DownloadManager.Request(uri)
            request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI or
                        DownloadManager.Request.NETWORK_MOBILE
            )

// set title and description

// set title and description
            request.setTitle("Data Download")
            request.setDescription("Android Data download using DownloadManager.")


            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

//set the local destination for download file to a path within the application's external files directory

//set the local destination for download file to a path within the application's external files directory
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "downloadfileName"
            )
            request.setMimeType("*/*")
            downloadManager!!.enqueue(request)

            Toast.makeText(context,"The file has been downloaded",Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int {
        return EmployeePayrollFragment.payrollList.size
    }

//    private fun setDataToView(clientId: TextView, clientName: TextView, dataPosition: Int) {
//        clientId.setText(EmployeeClientFragment.clientList[dataPosition].clientId)
//        clientName.text = EmployeeClientFragment.clientList[dataPosition].clientName
//
//
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun updateData(clientList: ArrayList<FindClient>) {
//        EmployeeClientFragment.clientList = clientList
//        notifyDataSetChanged()
//    }


}