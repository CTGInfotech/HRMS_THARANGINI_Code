package com.example.retrofitauthtoken.response

import com.example.retrofitauthtoken.models.Payload
import com.google.gson.annotations.SerializedName

data class LogoutResponse (
    @SerializedName("message" ) var message : String?,
    @SerializedName("payload" ) var payload : Payload?
        )