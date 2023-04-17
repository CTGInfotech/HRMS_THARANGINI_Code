package com.example.retrofitauthtoken.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitauthtoken.adapters.HolidayAdapter
import com.example.retrofitauthtoken.databinding.ActivityHolidayBinding
import com.example.retrofitauthtoken.models.FindHolidays
import com.example.retrofitauthtoken.network.ApiClient
import com.example.retrofitauthtoken.response.ClientResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HolidayActivity : AppCompatActivity() {

    private val binding by lazy { ActivityHolidayBinding.inflate(layoutInflater) }
    private val adapter by lazy { HolidayAdapter(this) }

    companion object {
        var holidayList = ArrayList<FindHolidays>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        attachListeners()
        setUpAdapter()
        getHolidayListApi()
    }

    private fun attachListeners() {
        binding.holidayBackArrow.setOnClickListener {
            finish()
        }
    }

    private fun setUpAdapter() {
        binding.holidaysRv.adapter = adapter
        binding.holidaysRv.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getHolidayListApi() {
        binding.holidayProgress.visibility = View.VISIBLE
        val retIn = ApiClient.create(this)
        retIn.fetchHolidays().enqueue(object : Callback<ClientResponse> {
            override fun onFailure(call: Call<ClientResponse>, t: Throwable) {
                binding.holidayProgress.visibility = View.GONE

                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT)
                    .show()
                adapter.notifyDataSetChanged()
            }

            override fun onResponse(
                call: Call<ClientResponse>,
                response: Response<ClientResponse>
            ) {
                val findHolidaysResponse = response.body()

                if (findHolidaysResponse?.message != null) {

                    runOnUiThread {
                        binding.holidayProgress.visibility = View.GONE

                        Toast.makeText(
                            applicationContext,
                            findHolidaysResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    if (findHolidaysResponse.payload?.findHolidays != null) {

                        holidayList = findHolidaysResponse.payload!!.findHolidays
                        runOnUiThread {
                            binding.holidayProgress.visibility = View.GONE

                            Toast.makeText(
                                applicationContext,
                                findHolidaysResponse.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            adapter.notifyDataSetChanged()
                        }

                    } else {
                        runOnUiThread {
                            binding.holidayProgress.visibility = View.GONE

                            Toast.makeText(
                                applicationContext, findHolidaysResponse.message,
                                Toast.LENGTH_SHORT
                            ).show()

                            adapter.notifyDataSetChanged()
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
                       adapter.notifyDataSetChanged()

                    }
                }
//                else {
//                    runOnUiThread {
//                        binding.holidayProgress.visibility = View.GONE
//                        Toast.makeText(
//                            applicationContext,
//                            response.message(),
//                            Toast.LENGTH_SHORT
//                        ).show()
//
//                        adapter.notifyDataSetChanged()
//                    }
//                }
            }
        })
    }
}