package com.example.retrofitauthtoken.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.retrofitauthtoken.R
import com.example.retrofitauthtoken.databinding.ActivityDashBoardBinding
import com.example.retrofitauthtoken.fragments.*
import com.example.retrofitauthtoken.network.ApiClient
import com.example.retrofitauthtoken.response.LogoutResponse
import com.example.retrofitauthtoken.utils.SessionManager
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.O)
class DashBoardActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDashBoardBinding.inflate(layoutInflater) }
    private var calendarView: CalendarView? = null
    private lateinit var sessionManager: SessionManager

    companion object {
        private lateinit var fragmentToUpdate: Fragment
        private var currentFrag = R.id.dash_pos
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        attachListeners()
        calendarView()
        initUi()
    }


    private fun initUi() {

        binding.dashNav.inflateHeaderView(R.layout.nav_header_main)
        val mView: View = binding.dashNav.getHeaderView(0)
        val userName = mView.findViewById<View>(R.id.header_userName) as TextView
        val number = mView.findViewById<View>(R.id.header_userEmail) as TextView

        userName.text = sessionManager.getFirstName()
        number.text = sessionManager.fetchEmail()

        val userRole = sessionManager.getUserROle() ?: " "
        when (userRole) {
            "admin" -> {
                binding.dashNav.inflateMenu(R.menu.admin_menu)
                currentFrag = R.id.dash_pos
                fragHelper(binding.dashNav.menu.getItem(0))
            }
            else -> {
                binding.dashNav.inflateMenu(R.menu.employee_menu)
                currentFrag = R.id.dash_pos
                fragHelper(binding.dashNav.menu.getItem(0))
            }

        }
    }

    private fun attachListeners() {

        binding.dashNav.setNavigationItemSelectedListener {
            if (binding.dashNav.menu[0].subMenu[0].isChecked) {
                binding.dashNav.menu[0].subMenu[0].isChecked = false
            }
            navHelper(it)
            return@setNavigationItemSelectedListener true
        }
        binding.dashMenu.setOnClickListener {
            binding.dashDrawer.openDrawer(GravityCompat.START)
        }
        binding.dashMore.setOnClickListener {
            PopupMenu(this, binding.dashMore).apply {
                menuInflater.inflate(R.menu.user_profile_options, menu)
                setOnMenuItemClickListener {
                    navHelper(it)
                    return@setOnMenuItemClickListener true
                }
                show()
            }
        }
    }

    private fun navHelper(item: MenuItem) {
        when (item.itemId) {
            R.id.logout -> {
                sessionManager.setLoggedIn(false)
                logoutBtn()
            }
            R.id.profile -> {
                val intent = Intent(this, UserProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.change_password -> {
                val intent = Intent(this, ChangePasswordActivity::class.java)
                startActivity(intent)
            }
            else -> fragHelper(item)
        }
    }

    private fun fragHelper(item: MenuItem) {
        var fragment: Fragment? = null
        val fragmentClass: Class<*>

        val userType = sessionManager.getUserROle() ?: ""
        if (userType == "admin") {
            fragmentClass = when (item.itemId) {
                R.id.dash_employee -> AdminEmployeeFragment::class.java
                R.id.dash_OnBoardingEmp -> AdminOnBoardEmpFragment::class.java
                R.id.dash_project -> EmployeeProjectFragment::class.java
                else -> AdminDashFragment::class.java
            }
        } else {
            fragmentClass = when (item.itemId) {
                R.id.dash_calendar -> CalendarFragment::class.java
                R.id.dash_client -> EmployeeClientFragment::class.java
                R.id.dash_project -> EmployeeProjectFragment::class.java
                R.id.dash_leave -> EmployeeLeaveFragment::class.java
                R.id.dash_timesheet -> EmployeeTimesheetFragment::class.java
                R.id.dash_payroll -> EmployeePayrollFragment::class.java

                else -> EmpDashFragment::class.java
            }
        }

        try {
            fragment = fragmentClass.newInstance() as Fragment
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        if (fragment != null) {
            fragmentToUpdate = fragment
            supportFragmentManager.beginTransaction().replace(R.id.dash_container, fragment)
                .commit()
        }
        currentFrag = item.itemId
        item.isChecked = true
        handleMenuTitle(item)
        binding.dashDrawer.closeDrawer(GravityCompat.START)
    }


    @SuppressLint("SetTextI18n")
    private fun handleMenuTitle(item: MenuItem) {
        when (item.itemId) {
            R.id.dash_pos -> {
                binding.dashTitle.text = "DashBoard"
                binding.dashTitle.gravity = Gravity.CENTER_VERTICAL
            }
            R.id.dash_client -> {
                binding.dashTitle.text = "Client"
                binding.dashTitle.gravity = Gravity.CENTER_VERTICAL
            }
            R.id.dash_project -> {
                binding.dashTitle.text = "Project"
                binding.dashTitle.gravity = Gravity.CENTER_VERTICAL
            }
            R.id.dash_timesheet -> {
                binding.dashTitle.text = "Timesheet"
                binding.dashTitle.gravity = Gravity.CENTER_VERTICAL
            }
            R.id.dash_calendar -> {
                binding.dashTitle.text = "Calendar"
                binding.dashTitle.gravity = Gravity.CENTER_VERTICAL
            }
            R.id.dash_OnBoardingEmp -> {
                binding.dashTitle.text = "OnBoarding Employee"
                binding.dashTitle.gravity = Gravity.CENTER_VERTICAL
            }
            R.id.dash_employee -> {
                binding.dashTitle.text = "Employee"
                binding.dashTitle.gravity = Gravity.CENTER_VERTICAL
            }
            R.id.dash_events -> {
                binding.dashTitle.text = "Events"
                binding.dashTitle.gravity = Gravity.CENTER_VERTICAL
            }
            R.id.dash_holidays -> {
                binding.dashTitle.text = "Holidays"
                binding.dashTitle.gravity = Gravity.CENTER_VERTICAL
            }
            R.id.dash_leave -> {
                binding.dashTitle.text = "Leave Management"
                binding.dashTitle.gravity = Gravity.CENTER_VERTICAL
            }
            R.id.dash_payroll -> {
                binding.dashTitle.text = "Pay Roll"
                binding.dashTitle.gravity = Gravity.CENTER_VERTICAL
            }
//            else -> {
//                handleLocationBasedTitle(item)
//            }
        }
    }

    private fun logoutBtn() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are sure you want to Logout?")
        builder.setPositiveButton("Yes") { _, _ ->
            userLogout()
        }
        builder.setNegativeButton("no") { dialogInterface, _ ->
            dialogInterface.cancel()
        }
        builder.show()
    }

    private fun calendarView() {
        calendarView
            ?.setOnDateChangeListener { _, year, month, dayOfMonth ->
                (dayOfMonth.toString() + "-"
                        + (month + 1) + "-" + year)

            }

    }

    private fun userLogout() {
        binding.dashProgress.visibility = View.VISIBLE

        val retIn = ApiClient.create(applicationContext)

        retIn.signOut().enqueue(object : Callback<LogoutResponse> {
            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                runOnUiThread {
//                    initUi()
                    binding.dashProgress.visibility = View.GONE

                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onResponse(
                call: Call<LogoutResponse>,
                response: Response<LogoutResponse>
            ) {

                val loginResponse = response.body()
                if (response.code() == 200) {
                    runOnUiThread {

                        binding.dashProgress.visibility = View.GONE
                        Toast.makeText(
                            applicationContext,
                            loginResponse?.message,
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(applicationContext, LoginScreenActivity::class.java)
                        startActivity(intent)
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
//                else {
//                    runOnUiThread {
//                        binding.dashProgress.visibility = View.GONE
//                        Toast.makeText(
//                            applicationContext,
//                            response.message(),
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
            }
        })
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).setNegativeButton("No", null)
            .setPositiveButton("Yes") { _, _ ->
                finishAffinity()
            }
            .setMessage("Are you sure you want to Exit ?")
            .show()
    }
}

