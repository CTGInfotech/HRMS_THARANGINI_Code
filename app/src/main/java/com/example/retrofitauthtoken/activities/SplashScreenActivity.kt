package com.example.retrofitauthtoken.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import com.example.retrofitauthtoken.databinding.ActivitySplashScreenBinding
import com.example.retrofitauthtoken.utils.SessionManager
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
class SplashScreenActivity : AppCompatActivity() {

    private var timer: Timer? = null
    private lateinit var sessionManager: SessionManager
    private val binding by lazy { ActivitySplashScreenBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        setTimer()
    }
    private fun setTimer() {

        binding.splashProgress.visibility = View.VISIBLE
        timer = Timer()
        Handler().postDelayed({ attachListeners() }, 3000)
    }


    private fun attachListeners() {
        if (sessionManager.getLoggedStatus()) {
            val intent = Intent(applicationContext, DashBoardActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(applicationContext, LoginScreenActivity::class.java)
            startActivity(intent)
        }
    }
}