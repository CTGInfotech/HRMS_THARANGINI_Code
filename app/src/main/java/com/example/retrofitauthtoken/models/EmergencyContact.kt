package com.example.retrofitauthtoken.models

import com.google.gson.annotations.SerializedName


data class EmergencyContact (

    @SerializedName("emergencyContactEmail"         ) var emergencyContactEmail         : String?,
    @SerializedName("emergencyContactName1"         ) var emergencyContactName1         : String?,
    @SerializedName("emergencyContactRalationship1" ) var emergencyContactRalationship1 : String?,
    @SerializedName("emergencyContactPhon1"         ) var emergencyContactPhon1         : String?,
    @SerializedName("emergencyContactName2"         ) var emergencyContactName2         : String?,
    @SerializedName("emergencyContactRalationship2" ) var emergencyContactRalationship2 : String?,
    @SerializedName("emergencyContactPhon2"         ) var emergencyContactPhon2         : String?

)