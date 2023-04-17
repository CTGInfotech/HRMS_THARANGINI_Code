package com.example.retrofitauthtoken.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.retrofitauthtoken.R
import com.example.retrofitauthtoken.adapters.EmployeeClientAdapter
import com.example.retrofitauthtoken.adapters.EmployeePayrollAdapter
import com.example.retrofitauthtoken.databinding.FragmentEmployeePayrollBinding
import com.example.retrofitauthtoken.databinding.FragmentEmployeeProjectBinding
import com.example.retrofitauthtoken.models.FindClient
import com.example.retrofitauthtoken.models.FindPaySlips
import com.example.retrofitauthtoken.network.ApiClient
import com.example.retrofitauthtoken.response.ClientResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class EmployeePayrollFragment : Fragment() {

    private val binding by lazy { FragmentEmployeePayrollBinding.inflate(layoutInflater) }
    private val adapter by lazy { EmployeePayrollAdapter(requireContext()) }
    companion object {
        var payrollList = ArrayList<FindPaySlips>()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.payrollRv.adapter = adapter
        binding.payrollRv.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.payrollRv.adapter = adapter

        binding.payrollRv.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        getPayrollListApi()

    }

    private fun getPayrollListApi() {
        binding.clientProgress.visibility = View.VISIBLE

        val retIn = ApiClient.create(requireContext())
        retIn.getPaySlip().enqueue(object : Callback<ClientResponse> {
            override fun onFailure(call: Call<ClientResponse>, t: Throwable) {
                binding.clientProgress.visibility = View.GONE
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT)
                    .show()
                adapter.notifyDataSetChanged()

            }

            override fun onResponse(
                call: Call<ClientResponse>,
                response: Response<ClientResponse>
            ) {
                val findPayrollResponse = response.body()

                if (findPayrollResponse?.message != null) {

                    activity?.runOnUiThread {
                        binding.clientProgress.visibility = View.GONE

                        Toast.makeText(
                            requireContext(),
                            findPayrollResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    if (findPayrollResponse.payload?.findPaySlips != null) {


                        payrollList = findPayrollResponse.payload!!.findPaySlips



                        activity?.runOnUiThread {
                            binding.clientProgress.visibility = View.GONE

                            Toast.makeText(
                                requireContext(),
                                findPayrollResponse.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            adapter.notifyDataSetChanged()
                        }

                    } else {
                        activity?.runOnUiThread {
                            binding.clientProgress.visibility = View.GONE

                            Toast.makeText(
                                requireContext(), findPayrollResponse.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            adapter.notifyDataSetChanged()
                        }

                    }
                } else {
                    activity?.runOnUiThread {
                        binding.clientProgress.visibility = View.GONE

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