package com.example.retrofitauthtoken.models

import com.google.gson.annotations.SerializedName

data class FindHolidays (
    @SerializedName("isActive"      ) var isActive      : Boolean?,
    @SerializedName("_id"           ) var Id            : String?,
    @SerializedName("name"          ) var name          : String?,
    @SerializedName("description"   ) var description   : String?,
    @SerializedName("startDate"     ) var startDate     : String?,
    @SerializedName("endDate"       ) var endDate       : String?,
    @SerializedName("year"          ) var year          : Int?,
    @SerializedName("calenderColor" ) var calenderColor : String?
        )