package com.example.retrofitauthtoken.models

import com.google.gson.annotations.SerializedName

data class Address (
        @SerializedName("address1" ) var address1 : String?,
        @SerializedName("address2" ) var address2 : String?,
        @SerializedName("city"     ) var city     : String?,
        @SerializedName("state"    ) var state    : String?,
        @SerializedName("country"  ) var country  : String?,
        @SerializedName("zip"      ) var zip      : String?
        )