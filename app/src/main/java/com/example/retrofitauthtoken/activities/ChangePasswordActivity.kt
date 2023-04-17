package com.example.retrofitauthtoken.activities


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitauthtoken.databinding.ActivityChangePasswordBinding
import com.example.retrofitauthtoken.network.ApiClient
import com.example.retrofitauthtoken.requests.LoginRequest
import com.example.retrofitauthtoken.response.ForgotpassResponse
import com.example.retrofitauthtoken.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {

    private val binding by lazy { ActivityChangePasswordBinding.inflate(layoutInflater) }
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        attachListeners()
    }

    private fun attachListeners() {
        binding.changePasswordSubmit.setOnClickListener {
            validatePassword()
        }
    }
 
    private fun validatePassword() {
        val password = binding.newPassword.text ?: ""
        val confirmPassword = binding.confirmPassword.text ?: ""
        if (password.isNotEmpty() &&
            confirmPassword.isNotEmpty() && password.toString() == confirmPassword.toString()
        ) {
            updatePasswordApi(password.toString())
        } else {
            Toast.makeText(this, "Change password failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updatePasswordApi(password: String) {

        val retIn = ApiClient.create(applicationContext)
        retIn.updatePassword(
            LoginRequest(
                email = sessionManager.fetchEmail()!!,
                password = password
            )
        ).enqueue(object : Callback<ForgotpassResponse> {
            override fun onFailure(call: Call<ForgotpassResponse>, t: Throwable) {
                runOnUiThread {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<ForgotpassResponse>,
                response: Response<ForgotpassResponse>
            ) {
                val changePasswordResponse = response.body()
                if (changePasswordResponse?.message != null) {
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            changePasswordResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(applicationContext, DashBoardActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            response.message(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }
}