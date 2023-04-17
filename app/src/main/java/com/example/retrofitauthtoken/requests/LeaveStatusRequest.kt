package com.example.retrofitauthtoken.requests

data class LeaveStatusRequest (
    val leavesType: String,
    val description: String,
    val duration: String,
    val leaveDate: String
        )