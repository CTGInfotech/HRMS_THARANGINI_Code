package com.example.retrofitauthtoken.models

import com.google.gson.annotations.SerializedName

data class PayloadTimesheet (
    @SerializedName("createdAt"   ) var createdAt   : String?              = null,
    @SerializedName("status"      ) var status      : String?              = null,
    @SerializedName("_id"         ) var Id          : String?              = null,
    @SerializedName("user"        ) var user        : String?              = null,
    @SerializedName("clientName"  ) var clientName  : String?              = null,
    @SerializedName("projectName" ) var projectName : String?              = null,
    @SerializedName("month"       ) var month       : String?              = null,
    @SerializedName("timesheet"   ) var timesheet   : ArrayList<Timesheet> = arrayListOf(),
    @SerializedName("role"        ) var role        : String?              = null,
    @SerializedName("__v"         ) var _v          : Int?                 = null
        )