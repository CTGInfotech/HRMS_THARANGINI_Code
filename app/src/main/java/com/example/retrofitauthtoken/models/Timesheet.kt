package com.example.retrofitauthtoken.models

import com.google.gson.annotations.SerializedName

data class Timesheet (
    @SerializedName("date"        ) var date        : String?,
    @SerializedName("day"         ) var day         : String?,
    @SerializedName("work_hrs"    ) var workHrs     : String?,
    @SerializedName("away_hrs"    ) var awayHrs     : String?,
    @SerializedName("description" ) var description : String?

)