package com.example.retrofitauthtoken.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitauthtoken.databinding.ActivityForgotPasswordBinding
import com.example.retrofitauthtoken.network.ApiClient
import com.example.retrofitauthtoken.requests.ForgotPasswordRequest
import com.example.retrofitauthtoken.response.ForgotpassResponse
import com.example.retrofitauthtoken.utils.SessionManager
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : AppCompatActivity() {

    private val binding by lazy { ActivityForgotPasswordBinding.inflate(layoutInflater) }
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        attachListeners()
    }

    private fun attachListeners() {
        binding.forgotSubmit.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            getForgotPassword()
        }
    }

    private fun getForgotPassword() {
        val email = binding.forgotEmail.text.toString()
        val retIn = ApiClient.create(applicationContext)

        retIn.triggerMail(
            ForgotPasswordRequest(
                email = email
            )
        )
            .enqueue(object : Callback<ForgotpassResponse> {
                override fun onFailure(call: Call<ForgotpassResponse>, t: Throwable) {
                    runOnUiThread {
                        binding.progress.visibility = View.VISIBLE
                        binding.forgotSubmit.isEnabled = true
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onResponse(
                    call: Call<ForgotpassResponse>,
                    response: Response<ForgotpassResponse>
                ) {
                    runOnUiThread {
                        binding.progress.visibility = View.VISIBLE
                        binding.forgotSubmit.isEnabled = true
                    }
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                loginResponse.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            runOnUiThread {
                                binding.progress.visibility = View.VISIBLE
                                val intent =
                                    Intent(applicationContext, LoginScreenActivity::class.java)
                                startActivity(intent)
                                sessionManager.fetchAuthToken()
                            }
                        }

                    }
                    else{
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            Toast.makeText(applicationContext,jObjError.getString("message"),Toast.LENGTH_SHORT).show()

                        } catch (err: Exception) {
                            Toast.makeText(applicationContext, err.message, Toast.LENGTH_LONG).show();
                        }

                    }
//                    else {
//                        runOnUiThread {
//                            binding.progress.visibility = View.VISIBLE
//                            Toast.makeText(
//                                applicationContext,
//                                response.message(),
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
                }
            })
    }
}