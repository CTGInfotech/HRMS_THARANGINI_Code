package com.example.retrofitauthtoken.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.retrofitauthtoken.R
import com.example.retrofitauthtoken.activities.DashBoardActivity
import com.example.retrofitauthtoken.adapters.EmployeeClientAdapter
import com.example.retrofitauthtoken.adapters.EmployeeProjectAdapter
import com.example.retrofitauthtoken.databinding.FragmentEmployeeProjectBinding
import com.example.retrofitauthtoken.models.FindClient
import com.example.retrofitauthtoken.models.FindProject
import com.example.retrofitauthtoken.network.ApiClient
import com.example.retrofitauthtoken.response.ClientResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class EmployeeProjectFragment : Fragment() {

    private val binding by lazy { FragmentEmployeeProjectBinding.inflate(layoutInflater) }
    private val adapter by lazy { EmployeeProjectAdapter(requireContext()) }
    companion object{
        var projectList = ArrayList<FindProject>()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addClientFab.visibility = View.GONE
//        binding.projectBackArrow.setOnClickListener {
//            val intent = Intent(context, DashBoardActivity::class.java)
//            startActivity(intent)
//        }

        binding.projectRv.adapter = adapter

        binding.projectRv.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        getProjectListApi()

    }
    private fun getProjectListApi() {
        binding.projectProgress.visibility = View.VISIBLE
        val retIn = ApiClient.create(requireContext())
        retIn.projectDetails().enqueue(object : Callback<ClientResponse> {
            override fun onFailure(call: Call<ClientResponse>, t: Throwable) {

                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT)
                    .show()
                adapter.notifyDataSetChanged()

            }

            override fun onResponse(
                call: Call<ClientResponse>,
                response: Response<ClientResponse>
            ) {
                val findProjectResponse = response.body()

                if (findProjectResponse?.message != null) {

                    activity?.runOnUiThread {
                        binding.projectProgress.visibility = View.GONE

                        Toast.makeText(
                            requireContext(),
                            findProjectResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    if (findProjectResponse.payload?.findProject != null) {


                        projectList = findProjectResponse.payload?.findProject!!



                        activity?.runOnUiThread {
                            binding.projectProgress.visibility = View.GONE

                            Toast.makeText(
                                requireContext(),
                                findProjectResponse.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            adapter.notifyDataSetChanged()
                        }

                    } else {
                        activity?.runOnUiThread {
                            binding.projectProgress.visibility = View.GONE

                            Toast.makeText(
                                requireContext(), findProjectResponse.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            adapter.notifyDataSetChanged()
                        }

                    }
                } else {
                    activity?.runOnUiThread {
                        binding.projectProgress.visibility = View.GONE

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