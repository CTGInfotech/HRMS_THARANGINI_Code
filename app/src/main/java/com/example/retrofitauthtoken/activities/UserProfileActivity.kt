package com.example.retrofitauthtoken.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitauthtoken.databinding.ActivityUserProfileBinding

class UserProfileActivity : AppCompatActivity() {

    private val binding by lazy { ActivityUserProfileBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        attachListeners()
    }

    private fun attachListeners() {
        binding.userBasicInformation.setOnClickListener {
            val intent = Intent(this, UserBasicInfoActivity::class.java)
            startActivity(intent)
        }
    }
}