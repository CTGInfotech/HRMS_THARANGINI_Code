package com.example.retrofitauthtoken.network

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

class ApiClient {

    companion object {

//      var BASE_URL = "https://hrms-backend-api-dev.herokuapp.com/"
        var BASE_URL = "https://hrclocks.com"

        fun create(context: Context) : ApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okhttpClient(context))
                .build()
            return retrofit.create(ApiService::class.java)
        }

        private fun okhttpClient(context: Context): OkHttpClient {
            return OkHttpClient.Builder()

                .addInterceptor(AuthInterceptor(context))
                .build()
        }
    }
    /**
     * Initialize OkhttpClient with our interceptor
     */


}
