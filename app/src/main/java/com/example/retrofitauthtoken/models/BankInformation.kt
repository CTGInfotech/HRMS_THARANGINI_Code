package com.example.retrofitauthtoken.models

import com.google.gson.annotations.SerializedName

data class BankInformation (
    @SerializedName("bankAccountNo" ) var bankAccountNo : String?,
    @SerializedName("bankName"      ) var bankName      : String?,
    @SerializedName("bankBranch"    ) var bankBranch    : String?,
    @SerializedName("ifscCode"      ) var ifscCode      : String?
        )