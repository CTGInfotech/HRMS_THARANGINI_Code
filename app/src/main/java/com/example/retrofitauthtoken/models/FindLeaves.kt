package com.example.retrofitauthtoken.models

import com.google.gson.annotations.SerializedName

data class FindLeaves (

    @SerializedName("leavesType"  ) var leavesType  : String?,
    @SerializedName("leaveStatus" ) var leaveStatus : String?,
    @SerializedName("_id"         ) var Id          : String?,
    @SerializedName("description" ) var description : String?,
    @SerializedName("appliedAt"   ) var appliedAt   : String?,
    @SerializedName("appliedBy"   ) var appliedBy   : AppliedBy?,
    @SerializedName("duration"    ) var duration    : String?,
    @SerializedName("leaveDate"   ) var leaveDate   : String?

)