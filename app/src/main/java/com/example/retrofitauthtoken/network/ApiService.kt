package com.example.retrofitauthtoken.network

import com.example.retrofitauthtoken.models.Timesheet
import com.example.retrofitauthtoken.models.TimesheetResponse
import com.example.retrofitauthtoken.models.User
import com.example.retrofitauthtoken.requests.ForgotPasswordRequest
import com.example.retrofitauthtoken.requests.LeaveStatusRequest
import com.example.retrofitauthtoken.requests.LoginRequest
import com.example.retrofitauthtoken.requests.TimesheetRequest
import com.example.retrofitauthtoken.response.ClientResponse
import com.example.retrofitauthtoken.response.ForgotpassResponse
import com.example.retrofitauthtoken.response.LoginResponse
import com.example.retrofitauthtoken.response.LogoutResponse
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @POST("api/v1/user/login")
    fun authLogin(@Body request: LoginRequest): retrofit2.Call<LoginResponse>

    @POST("/api/v1/user/forgot")
    fun triggerMail(@Body request: ForgotPasswordRequest): retrofit2.Call<ForgotpassResponse>

    @PUT("/api/v1/user/logout")
    fun signOut(): retrofit2.Call<LogoutResponse>

    @POST("/api/v1/user/change/:resetToken")
    fun updatePassword(@Body request: LoginRequest):retrofit2.Call<ForgotpassResponse>

    @GET("/api/v1/client/getAllClients?page=1&limit=10")
    fun clientDetails(): retrofit2.Call<ClientResponse>

    @GET("/api/v1/project/getAllProjects?page=1&limit=10")
    fun projectDetails():retrofit2.Call<ClientResponse>

    @GET("/api/v1/holiday?page=1&limit=10")
    fun fetchHolidays():retrofit2.Call<ClientResponse>

    @POST("/api/v1/leave/add")
    fun addLeaves(@Body request: LeaveStatusRequest): retrofit2.Call<ClientResponse>

    @GET("/api/v1/leave?page=1&limit=10")
    fun getAllLeaves():retrofit2.Call<ClientResponse>

    @GET("/api/v1/user/upcoming-birthday?page=1&limit=10")
    fun getAllBirthdays(): retrofit2.Call<ClientResponse>

    @POST("/api/v1/timesheet/timesheetFilled")
    fun fillTimesheet(@Body request: TimesheetRequest): retrofit2.Call<TimesheetResponse>

    @GET("/api/v1/timesheet/getTimesheetByMonth/:yyyy-mm")
    fun getTimesheetByMonth():Call<ClientResponse>

    @GET("/api/v1/pay-slip/")
    fun getPaySlip(): Call<ClientResponse>
}