package com.example.retrofitauthtoken.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.retrofitauthtoken.R
import com.example.retrofitauthtoken.activities.AddLeaveActivity
import com.example.retrofitauthtoken.adapters.EmployeeLeaveManagementAdapter
import com.example.retrofitauthtoken.databinding.FragmentEmployeeLeaveBinding
import com.example.retrofitauthtoken.models.FindLeaves
import com.example.retrofitauthtoken.network.ApiClient
import com.example.retrofitauthtoken.response.ClientResponse
import com.example.retrofitauthtoken.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class EmployeeLeaveFragment : Fragment() {

    private val binding by lazy { FragmentEmployeeLeaveBinding.inflate(layoutInflater) }
    private val adapter by lazy { EmployeeLeaveManagementAdapter(requireContext()) }

    private lateinit var sessionManager: SessionManager

    companion object{
        var leavesList = ArrayList<FindLeaves>()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sessionManager = SessionManager(requireContext())
//        binding.remainingPaidLeaves.setText(String.va).toString()
//        binding.usedPaidLeaves.setText(sessionManager.getUsedLeaves()).toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val remainingPaidLeaves: String = sessionManager.getRemainingLeaves().toString()
        binding.remainingPaidLeaves.setText(remainingPaidLeaves)

        val usedLeaves: String = sessionManager.getUsedLeaves().toString()
        binding.usedPaidLeaves.setText(usedLeaves)
        binding.addLeavesFab.setOnClickListener{
         addLeaveAlertDialog()
        }
        binding.leaveManagementRv.adapter = adapter

        binding.leaveManagementRv.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
getLeavesListApi()
    }

    private fun addLeaveAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Do you want to add Leave?")
        builder.setPositiveButton("Yes") { _, _ ->
            val intent = Intent(context, AddLeaveActivity::class.java)
            startActivity(intent)
        }
        builder.setNegativeButton("no") { dialogInterface, _ ->
            dialogInterface.cancel()
        }
        builder.show()
    }
    private fun getLeavesListApi() {
        binding.leaveProgress.visibility = View.VISIBLE
        val retIn = ApiClient.create(requireContext())
        retIn.getAllLeaves().enqueue(object : Callback<ClientResponse> {
            override fun onFailure(call: Call<ClientResponse>, t: Throwable) {
                binding.leaveProgress.visibility = View.GONE

                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT)
                    .show()
                adapter.notifyDataSetChanged()

            }

            override fun onResponse(
                call: Call<ClientResponse>,
                response: Response<ClientResponse>
            ) {
                val findLeavesResponse = response.body()

                if (findLeavesResponse?.message != null) {

                    activity?.runOnUiThread {
                        binding.leaveProgress.visibility = View.GONE

                        Toast.makeText(
                            requireContext(),
                            findLeavesResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    if (findLeavesResponse.payload?.findLeaves != null) {


                        leavesList = findLeavesResponse.payload?.findLeaves!!



                        activity?.runOnUiThread {
                            binding.leaveProgress.visibility = View.GONE

                            Toast.makeText(
                                requireContext(),
                                findLeavesResponse.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            adapter.notifyDataSetChanged()
                        }

                    } else {
                        activity?.runOnUiThread {
                            binding.leaveProgress.visibility = View.GONE

                            Toast.makeText(
                                requireContext(), findLeavesResponse.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            adapter.notifyDataSetChanged()
                        }

                    }
                } else {
                    activity?.runOnUiThread {
                        binding.leaveProgress.visibility = View.GONE

                        Toast.makeText(
                            requireContext(),
                            response.message(),
                            Toast.LENGTH_SHORT
                        ).show()

                        adapter.notifyDataSetChanged()
                    }
                }

            }


        })
    }


}