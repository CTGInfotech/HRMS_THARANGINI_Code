package com.example.retrofitauthtoken.models

import com.google.gson.annotations.SerializedName

data class TimesheetResponse (
    @SerializedName("result"  ) var result  : Int?     = null,
    @SerializedName("message" ) var message : String?  = null,
    @SerializedName("payload" ) var payload : PayloadTimesheet? = PayloadTimesheet()
        )