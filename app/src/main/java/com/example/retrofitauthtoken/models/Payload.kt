package com.example.retrofitauthtoken.models

import com.google.gson.annotations.SerializedName

data class Payload(
    @SerializedName("user"  ) var user  : User?,
    @SerializedName("token" ) var token : String?,
    @SerializedName("findClient" ) var findClient : ArrayList<FindClient> = arrayListOf(),
    @SerializedName("findProject" ) var findProject : ArrayList<FindProject> = arrayListOf(),
    @SerializedName("findHolidays" ) var findHolidays : ArrayList<FindHolidays> = arrayListOf(),
    @SerializedName("leavesType"  ) var leavesType  : String?,
    @SerializedName("leaveStatus" ) var leaveStatus : String?,
    @SerializedName("description" ) var description : String?,
    @SerializedName("appliedAt"   ) var appliedAt   : String?,
    @SerializedName("appliedBy"   ) var appliedBy   : String?,
    @SerializedName("duration"    ) var duration    : String?,
    @SerializedName("leaveDate"   ) var leaveDate   : String?,
    @SerializedName("findLeaves"  ) var findLeaves  : ArrayList<FindLeaves> = arrayListOf(),

    @SerializedName("findEvents" ) var findEvents : ArrayList<FindEvents> = arrayListOf(),
    @SerializedName("findPaySlips" ) var findPaySlips : ArrayList<FindPaySlips> = arrayListOf()


)