package com.example.retrofitauthtoken.models

import com.google.gson.annotations.SerializedName


data class FindPaySlips (
    @SerializedName("_id"               ) var Id                : String? = null,
    @SerializedName("userId"            ) var userId            : Int?    = null,
    @SerializedName("salaryMonth"       ) var salaryMonth       : String? = null,
    @SerializedName("salaryYear"        ) var salaryYear        : Int?    = null,
    @SerializedName("payedToEmployeeId" ) var payedToEmployeeId : String? = null,
    @SerializedName("paySlip"           ) var paySlip           : String? = null,
    @SerializedName("addedBy"           ) var addedBy           : String? = null,
    @SerializedName("addedAt"           ) var addedAt           : String? = null,
    @SerializedName("__v"               ) var _v                : Int?    = null

)