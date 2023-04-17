package com.example.retrofitauthtoken.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.retrofitauthtoken.adapters.EmployeeClientAdapter
import com.example.retrofitauthtoken.databinding.FragmentEmployeeClientBinding
import com.example.retrofitauthtoken.models.FindClient
import com.example.retrofitauthtoken.network.ApiClient
import com.example.retrofitauthtoken.response.ClientResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class EmployeeClientFragment : Fragment() {

    private val binding by lazy { FragmentEmployeeClientBinding.inflate(layoutInflater) }
    private val adapter by lazy { EmployeeClientAdapter(requireContext()) }

    companion object {
        var clientList = ArrayList<FindClient>()
        var list = ArrayList<FindClient>()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.searchList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                var newText = newText
                if (newText == null || newText.trim { it <= ' ' }.isEmpty()) {
//                    adapter.setFilter(list)
                    adapter.updateData(clientList)
                    return false

                }
                newText = newText.toLowerCase(Locale.getDefault())
                val filteredNewsList: ArrayList<FindClient> = ArrayList()
                for (model in clientList) {
                    val title: String = model.clientName?.toLowerCase().toString()

                    if (title.contains(newText)

                    ) {
                        filteredNewsList.add(model)

                    }

                    adapter.notifyDataSetChanged()
//
                }

//                adapter.setFilter(filteredNewsList)
                adapter.updateData(filteredNewsList)


                return true

            }
        })

        binding.clientRv.adapter = adapter
        binding.clientRv.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addClientFab.visibility = View.GONE

        binding.clientRv.adapter = adapter

        binding.clientRv.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        getClientListApi()

    }

    override fun onResume() {
        super.onResume()
        getClientListApi()
    }


    private fun getClientListApi() {
        binding.clientProgress.visibility = View.VISIBLE

        val retIn = ApiClient.create(requireContext())
        retIn.clientDetails().enqueue(object : Callback<ClientResponse> {
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
                val findClientResponse = response.body()

                if (findClientResponse?.message != null) {

                    activity?.runOnUiThread {
                        binding.clientProgress.visibility = View.GONE

                        Toast.makeText(
                            requireContext(),
                            findClientResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    if (findClientResponse.payload?.findClient != null) {


                        clientList = findClientResponse.payload!!.findClient



                        activity?.runOnUiThread {
                            binding.clientProgress.visibility = View.GONE

                            Toast.makeText(
                                requireContext(),
                                findClientResponse.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            adapter.notifyDataSetChanged()
                        }

                    } else {
                        activity?.runOnUiThread {
                            binding.clientProgress.visibility = View.GONE

                            Toast.makeText(
                                requireContext(), findClientResponse.message,
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




