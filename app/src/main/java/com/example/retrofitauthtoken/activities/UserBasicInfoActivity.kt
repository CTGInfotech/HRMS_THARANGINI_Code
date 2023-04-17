package com.example.retrofitauthtoken.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitauthtoken.databinding.ActivityUserBasicInfoBinding
import com.example.retrofitauthtoken.utils.SessionManager

class UserBasicInfoActivity : AppCompatActivity() {

    private val binding by lazy { ActivityUserBasicInfoBinding.inflate(layoutInflater) }
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        attachListeners()
    }

    private fun attachListeners() {
        binding.userFirstName.setText(sessionManager.getFirstName())
        binding.userLastName.setText(sessionManager.getLastName())
        binding.userFatherName.setText(sessionManager.getFatherName())
        binding.userMotherName.setText(sessionManager.getMotherName())
        binding.userEmpDob.setText(sessionManager.getUserDOB())
        binding.addEmpNation.setText(sessionManager.getUserNationality())
        binding.userEmpGender.setText(sessionManager.getUserGender())
        binding.addEmpEmailId.setText(sessionManager.fetchEmail())
        binding.addEmpAddress1.setText(sessionManager.getUserAddress1())
        binding.addEmpAddress2.setText(sessionManager.getUserAddress2())
        binding.addEmpState.setText(sessionManager.getUserState())
        binding.addEmpCountry.setText(sessionManager.getUserCountry())
        binding.addEmpCity.setText(sessionManager.getUserCity())
        binding.addEmpBloodGrp.setText(sessionManager.getUserBloodGrp())
        binding.addEmpPanCard.setText(sessionManager.getUserPanNo())
        binding.addEmpAadhaarNo.setText(sessionManager.getUserAadhaarNo())
    }
}