package com.example.retrofitauthtoken.activities

import android.R.layout
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitauthtoken.databinding.ActivityMain3Binding
import com.example.retrofitauthtoken.models.FindClient
import com.google.gson.JsonArray
import com.google.gson.JsonIOException


class MainActivity3 : AppCompatActivity() {
    private val binding by lazy { ActivityMain3Binding.inflate(layoutInflater) }
    var integerList: List<FindClient>? = null
    private var spinner: Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        spinner = binding.spinnerlist
        integerList = ArrayList()

    }

}