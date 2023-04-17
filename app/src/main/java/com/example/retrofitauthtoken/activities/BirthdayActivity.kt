package com.example.retrofitauthtoken.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.retrofitauthtoken.databinding.ActivityBirthdayBinding

class BirthdayActivity : AppCompatActivity() {

    private val binding by lazy { ActivityBirthdayBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}