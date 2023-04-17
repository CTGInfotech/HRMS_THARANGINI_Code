package com.example.retrofitauthtoken.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitauthtoken.databinding.ActivityLoginScreenBinding
import com.example.retrofitauthtoken.network.ApiClient
import com.example.retrofitauthtoken.requests.LoginRequest
import com.example.retrofitauthtoken.response.LoginResponse
import com.example.retrofitauthtoken.utils.SessionManager
import com.google.gson.GsonBuilder
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.O)
class LoginScreenActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginScreenBinding.inflate(layoutInflater) }
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        attachListeners()
    }

    private fun attachListeners() {
        if (sessionManager.getLoggedStatus()) {
            val intent = Intent(applicationContext, DashBoardActivity::class.java)
            startActivity(intent)
        } else {
            binding.loginForm.visibility = View.VISIBLE
        }
        binding.signIn.setOnClickListener {
            if (validateUser()) {
                binding.loginProgress.visibility = View.VISIBLE
                binding.signIn.isEnabled = false
                loginApiCall()
            }
        }

        binding.forgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    binding.passwordLayout.error = null
                }
            }
        })

        binding.email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    binding.userLayout.error = null
                }
            }
        })

    }

    private fun validateUser(): Boolean {
        var isValid = true
        if (binding.email.text.toString()
                .isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(binding.email.text.toString())
                .matches()
        ) {
            binding.userLayout.error = "Enter valid email id"
            isValid = false
        }
        if (binding.password.text.toString().isEmpty()) {
            binding.passwordLayout.error = "Password should not be empty"
            isValid = false
        } else if (binding.password.text.toString().length < 6) {
            binding.passwordLayout.error = "Password must be at least 6 characters"
            isValid = false
        }
        return isValid
    }

    private fun loginApiCall() {
        binding.loginProgress.visibility = View.VISIBLE
        val userEmail = binding.email.text.toString()
        val userPass = binding.password.text.toString()

        val retIn = ApiClient.create(applicationContext)

        retIn.authLogin(
            LoginRequest(
                email = userEmail,
                password = userPass
            )
        )
            .enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    runOnUiThread {
                        binding.loginProgress.visibility = View.GONE
                        Toast.makeText(this@LoginScreenActivity, t.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }


                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    runOnUiThread {
                        binding.loginProgress.visibility = View.GONE
                        binding.signIn.isEnabled = true

                    }
                    val loginResponse = response.body()
                    if (loginResponse?.message != null) {
                        runOnUiThread {
                            binding.loginProgress.visibility = View.GONE
                            Toast.makeText(
                                this@LoginScreenActivity,
                                loginResponse.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
//                        if (response.code() == 400) {
//                         runOnUiThread {
//                             Log.v("Error code 400",response.errorBody()!!.string());
//                         }

                            if (loginResponse.payload?.user != null) {
                                runOnUiThread {
                                    binding.loginProgress.visibility = View.GONE
                                    sessionManager.setLoggedIn(true)

                                    sessionManager.saveAuthToken(loginResponse.payload!!.token!!)
                                    sessionManager.setUserID(loginResponse.payload?.user?.Id!!)
                                    sessionManager.saveEmail(loginResponse.payload?.user?.email!!)
                                    sessionManager.setFirstName(loginResponse.payload?.user?.firstName!!)
                                    sessionManager.setLastName(loginResponse.payload?.user?.lastName!!)
                                    sessionManager.setUserRole(loginResponse.payload?.user?.role!!)
                                    sessionManager.setMotherName(
                                        loginResponse.payload?.user?.motherName ?: ""
                                    )
                                    sessionManager.setFatherName(
                                        loginResponse.payload?.user?.fatherName ?: ""
                                    )
                                    sessionManager.setUserDOB(
                                        loginResponse.payload?.user?.dob ?: ""
                                    )
                                    sessionManager.setRemainingLeaves(loginResponse.payload?.user?.remainingPaidLeaves!!)
                                    sessionManager.setUsedLeaves(loginResponse.payload?.user?.usedPaidLeaves!!)
                                    sessionManager.setUserNationality(loginResponse.payload?.user?.nationality!!)
                                    sessionManager.setUserGender(loginResponse.payload?.user?.gender!!)
                                    sessionManager.setUserAddress1(loginResponse.payload?.user?.address?.address1.toString())
                                    sessionManager.setUserAddress2(loginResponse.payload?.user?.address?.address2.toString())
                                    sessionManager.setUserCity(loginResponse.payload?.user?.address?.city.toString())
                                    sessionManager.setUserCountry(loginResponse.payload?.user?.address?.country.toString())
                                    sessionManager.setUserState(loginResponse.payload?.user?.address?.state.toString())
                                    sessionManager.setUserBloodGrp(loginResponse.payload?.user?.bloodGroup.toString())
//                                    sessionManager.setUserAadhaarNo(loginResponse.payload!!.user!!.adharcardNo!!)
//                                    sessionManager.setUserPanNo(loginResponse.payload?.user?.pancardNo!!)

                                    val intent =
                                        Intent(applicationContext, DashBoardActivity::class.java)
                                    startActivity(intent)
                                }
                            } else {
                                runOnUiThread {

                                    binding.loginProgress.visibility = View.GONE
                                    Toast.makeText(
                                        this@LoginScreenActivity,
                                        response.errorBody().toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
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
                        runOnUiThread {
                            binding.loginProgress.visibility = View.GONE

                        }
                    }

//                    else {
//                        runOnUiThread {
//                            binding.loginProgress.visibility = View.GONE
//                            Toast.makeText(
//                                this@LoginScreenActivity,
//                                response.errorBody()?.toString(),
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }

                }

            })
    }
}


