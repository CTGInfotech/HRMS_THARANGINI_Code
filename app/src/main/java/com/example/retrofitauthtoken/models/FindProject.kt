package com.example.retrofitauthtoken.models

import com.google.gson.annotations.SerializedName

data class FindProject(
    @SerializedName("_id") var Id: String?,
    @SerializedName("projectCode") var projectCode: String?,
    @SerializedName("projectName") var projectName: String?,
    @SerializedName("projectDesc") var projectDesc: String?,
    @SerializedName("startDate") var startDate: String?,
    @SerializedName("endDate") var endDate: String?,
    @SerializedName("client") var client: Client?
)
