package com.example.retrofitauthtoken.response

import com.example.retrofitauthtoken.models.Payload
import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("message" ) var message : String?,
    @SerializedName("payload" ) var payload : Payload?
)