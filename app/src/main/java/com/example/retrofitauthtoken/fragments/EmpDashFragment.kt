package com.example.retrofitauthtoken.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.retrofitauthtoken.R
import com.example.retrofitauthtoken.activities.BirthdayActivity
import com.example.retrofitauthtoken.activities.HolidayActivity
import com.example.retrofitauthtoken.databinding.FragmentEmpDashBinding


class EmpDashFragment : Fragment() {

    private val binding by lazy { FragmentEmpDashBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.empHolidayList.setOnClickListener {
            val intent = Intent(context, HolidayActivity::class.java)
            startActivity(intent)
        }

        binding.empUpcomingBirthday.setOnClickListener {
            val intent = Intent(context, BirthdayActivity::class.java)
            startActivity(intent)
        }
    }
}