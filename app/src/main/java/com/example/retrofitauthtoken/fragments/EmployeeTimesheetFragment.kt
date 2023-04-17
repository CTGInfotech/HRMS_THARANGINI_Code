package com.example.retrofitauthtoken.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.retrofitauthtoken.R
import com.example.retrofitauthtoken.activities.EmployeeFillTimesheetActivity
import com.example.retrofitauthtoken.databinding.FragmentEmployeeTimesheetBinding
import com.example.retrofitauthtoken.models.Timesheet

class EmployeeTimesheetFragment : Fragment() {

    private val binding by lazy { FragmentEmployeeTimesheetBinding.inflate(layoutInflater) }
    companion object{
        var timeSheetList = ArrayList<Timesheet>()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       return binding.root
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fillTimesheetFab.setOnClickListener {
            val intent = Intent(context,EmployeeFillTimesheetActivity::class.java)
            startActivity(intent)
        }

    }

}