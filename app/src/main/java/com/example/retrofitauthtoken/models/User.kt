package com.example.retrofitauthtoken.models

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("_id"                     ) var Id                      : String?,
    @SerializedName("firstName"               ) var firstName               : String,
    @SerializedName("lastName"                ) var lastName                : String,
    @SerializedName("email"                   ) var email                   : String,
    @SerializedName("password"                ) var password                : String,
    @SerializedName("role"                    ) var role                    : String?,
    @SerializedName("isLoggedIn"              ) var isLoggedIn              : Boolean?,
    @SerializedName("address"                 ) var address                 : Address?,
    @SerializedName("bankinformation"         ) var bankinformation         : BankInformation?,
    @SerializedName("emergencyContact"        ) var emergencyContact        : EmergencyContact?,
    @SerializedName("isBlock"                 ) var isBlock                 : Boolean?,
    @SerializedName("isActive"                ) var isActive                : Boolean?,
    @SerializedName("remainingPaidLeaves"     ) var remainingPaidLeaves     : Int?,
    @SerializedName("usedPaidLeaves"          ) var usedPaidLeaves          : Int?,
    @SerializedName("dob"                     ) var dob                     : String?,
    @SerializedName("nationality"             ) var nationality             : String?,
    @SerializedName("phone"                   ) var phone                   : String?,
    @SerializedName("gender"                  ) var gender                  : String?,
    @SerializedName("resetPasswordToken"      ) var resetPasswordToken      : String?,
    @SerializedName("adharcardNo"             ) var adharcardNo             : String?,
    @SerializedName("bloodGroup"              ) var bloodGroup              : String?,
    @SerializedName("department"              ) var department              : String?,
    @SerializedName("designation"             ) var designation             : String?,
    @SerializedName("employeePhoto"           ) var employeePhoto           : String?,
    @SerializedName("fatherName"              ) var fatherName              : String?,
    @SerializedName("joiningDate"             ) var joiningDate             : String?,
    @SerializedName("motherName"              ) var motherName              : String?,
    @SerializedName("officialEmail"           ) var officialEmail           : String?,
    @SerializedName("pancardNo"               ) var pancardNo               : String?,
    @SerializedName("phone1"                  ) var phone1                  : String?,
    @SerializedName("reportingTo"             ) var reportingTo             : String?,
    @SerializedName("userId"                  ) var userId                  : String?,
    @SerializedName("workType"                ) var workType                : String?
)