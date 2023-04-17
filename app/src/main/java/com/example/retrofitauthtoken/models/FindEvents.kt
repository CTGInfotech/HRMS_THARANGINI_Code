package com.example.retrofitauthtoken.models

import com.google.gson.annotations.SerializedName

data class FindEvents (

    @SerializedName("isUpdated"     ) var isUpdated     : Boolean?,
    @SerializedName("_id"           ) var Id            : String?,
    @SerializedName("name"          ) var name          : String?,
    @SerializedName("description"   ) var description   : String?,
    @SerializedName("startDate"     ) var startDate     : String?,
    @SerializedName("endDate"       ) var endDate       : String?,
    @SerializedName("isActive"      ) var isActive      : Boolean?,
    @SerializedName("postDate"      ) var postDate      : String?,
    @SerializedName("postBy"        ) var postBy        : String?,
    @SerializedName("duration"      ) var duration      : String?,
    @SerializedName("location"      ) var location      : String?,
    @SerializedName("eventTime"     ) var eventTime     : String?,
    @SerializedName("calenderColor" ) var calenderColor : String?

)