package com.example.retrofitauthtoken.requests

import com.example.retrofitauthtoken.models.Timesheet
import com.google.gson.annotations.SerializedName

data class TimesheetRequest (
    @SerializedName("clientName"  ) var clientName  : String?,
    @SerializedName("projectName" ) var projectName : String?,
    @SerializedName("month"       ) var month       : String?,
    @SerializedName("timesheet"   ) var timesheet   : ArrayList<Timesheet> = arrayListOf()
)