package com.example.retrofitauthtoken.models

import com.google.gson.annotations.SerializedName

data class Client (
    @SerializedName("_id"        ) var Id         : String?,
    @SerializedName("clientId"   ) var clientId   : String?,
    @SerializedName("clientName" ) var clientName : String?
        )