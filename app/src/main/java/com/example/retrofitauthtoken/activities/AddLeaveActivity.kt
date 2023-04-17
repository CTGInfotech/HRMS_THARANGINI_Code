package com.example.retrofitauthtoken.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitauthtoken.R
import com.example.retrofitauthtoken.databinding.ActivityAddLeaveBinding
import com.example.retrofitauthtoken.network.ApiClient
import com.example.retrofitauthtoken.requests.LeaveStatusRequest
import com.example.retrofitauthtoken.response.ClientResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddLeaveActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddLeaveBinding.inflate(layoutInflater) }
//    private val isEdit by lazy { intent.getBooleanExtra("isEdit", false) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        attachListeners()
        setUpSpinnerAdapter()
    }

    @SuppressLint("SetTextI18n")
    private fun attachListeners() {
        binding.leaveDate.setOnClickListener {
            val inflater: LayoutInflater = (LayoutInflater.from(this))
            val view = inflater.inflate(R.layout.dialog_date, null)
            val dialog = AlertDialog.Builder(this).setView(view)
                .setPositiveButton("Done") { _, _ ->
                    val title = view.findViewById<DatePicker>(R.id.date_picker)
                    binding.leaveDate.setText(
                        title.dayOfMonth.toString() + "-" + (title.month + 1).toString() + "-" + title.year.toString()
                    )
                }
                .create()
            dialog.show()
        }

        binding.addLeaveBtn.setOnClickListener {
            addLeaveAlertDialog()
        }

        val title = findViewById<TextView>(R.id.top_header_title)
        title.text = "Leave Request Form"
        val arrow = findViewById<ImageView>(R.id.items_arrow_back)
        arrow.setOnClickListener {
            finish()
        }
    }

    private fun setUpSpinnerAdapter() {
        val languages = resources.getStringArray(R.array.Languages)
        val arrayAdapter: ArrayAdapter<*> =
            ArrayAdapter<Any?>(this, android.R.layout.simple_dropdown_item_1line, languages)
        binding.leaveTypes.setAdapter(arrayAdapter)

    }

    private fun addLeaveAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do you want to add Leave?")
        builder.setPositiveButton("Yes") { _, _ ->
            addLeaveApi()
        }
        builder.setNegativeButton("no") { dialogInterface, _ ->
            dialogInterface.cancel()
        }
        builder.show()
        
    }

    private fun addLeaveApi() {
        val retIn = ApiClient.create(applicationContext)

        val leaveType = binding.leaveTypes.text.toString()
        val desc = binding.description.text.toString()
        val noOfDays = binding.noOfDays.text.toString()
        val leaveDate = binding.leaveDate.text.toString()
        retIn.addLeaves(
            LeaveStatusRequest(
                leavesType = leaveType,
                description = desc,
                duration = noOfDays,
                leaveDate = leaveDate
            )
        )
            .enqueue(object : Callback<ClientResponse> {
                override fun onFailure(call: Call<ClientResponse>, t: Throwable) {
                    runOnUiThread {
                        Toast.makeText(this@AddLeaveActivity, t.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onResponse(
                    call: Call<ClientResponse>,
                    response: Response<ClientResponse>
                ) {

                    val addLeaveResponse = response.body()
                    if (response.message() != null) {
                        runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                addLeaveResponse?.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    else{
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            Toast.makeText(applicationContext,jObjError.getString("message"),Toast.LENGTH_SHORT).show()

                        } catch (err: Exception) {
                            Toast.makeText(applicationContext, err.message, Toast.LENGTH_LONG).show()
                        }

                    }
//                    else {
//                        runOnUiThread {
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
