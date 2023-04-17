package com.example.retrofitauthtoken.models

import com.google.gson.annotations.SerializedName

data class AppliedBy (

    @SerializedName("role"                ) var role                : String?,
    @SerializedName("remainingPaidLeaves" ) var remainingPaidLeaves : Int?,
    @SerializedName("usedPaidLeaves"      ) var usedPaidLeaves      : Int?,
    @SerializedName("_id"                 ) var Id                  : String?,
    @SerializedName("firstName"           ) var firstName           : String?,
    @SerializedName("lastName"            ) var lastName            : String?,
    @SerializedName("email"               ) var email               : String?,
    @SerializedName("userId"              ) var userId              : Int?

)