package com.example.retrofitauthtoken.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.retrofitauthtoken.R

class SessionManager(context: Context) {


    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    val editor = prefs.edit()
    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_ID = "_id"
        const val LOGGED_IN_PREF = "isLoggedIn"
        const val EMAIL = "email"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val USER_ROLE = "user_role"
        const val MOTHER_NAME = "mother_name"
        const val FATHER_NAME = "father_name"
        const val EMP_DOB = "emp_dob"
        const val EMP_GENDER = "emp_gender"
//        const val EMP_EMAIL = "emp_emailId"
        const val NATIONALITY ="nationality"
        const val REMAINING_LEAVES ="remaining_paid_leaves"
        const val USED_LEAVES ="used_leaves"
        const val ADDRESS_1 = "address_1"
        const val ADDRESS_2 ="address_2"
        const val CITY ="city"
        const val STATE ="state"
        const val COUNTRY = "country"
        const val BLOOD_GRP = "Blood_group"
        const val AADHAAR_NO ="aadhaar_no"
        const val PAN_CARD = "pan_card"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
//        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun setUserID(_id: String){
        editor.putString(USER_ID, _id)
        editor.apply()
    }
    fun getUserId(): String? {
        return prefs.getString(USER_ID, null)
    }
    fun setFirstName(firstname: String){
        editor.putString(FIRST_NAME,firstname)
        editor.apply()
    }
    fun getFirstName(): String?{
        return prefs.getString(FIRST_NAME, null)
    }
    fun setLastName(lastname: String){
        editor.putString(LAST_NAME, lastname)
        editor.apply()
    }
    fun getLastName(): String?{
        return prefs.getString(LAST_NAME, null)
    }
    fun setUserRole(role: String){
        editor.putString(USER_ROLE,role)
        editor.apply()
    }
    fun getUserROle(): String?{
        return prefs.getString(USER_ROLE, null)
    }
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun setLoggedIn(loggedIn: Boolean) {
        editor.putBoolean(LOGGED_IN_PREF, loggedIn)
        editor.apply()
    }
    fun getLoggedStatus(): Boolean {
        return prefs.getBoolean(LOGGED_IN_PREF, false)
    }

    fun saveEmail(email: String){
        editor.putString(EMAIL, email)
        editor.apply()
    }
    fun fetchEmail(): String?{
       return prefs.getString(EMAIL, null)
    }
    fun setMotherName(motherName: String){
        editor.putString(MOTHER_NAME, motherName)
        editor.apply()
    }
    fun getMotherName(): String?{
        return prefs.getString(MOTHER_NAME, null)
    }
    fun setFatherName(fatherName: String){
        editor.putString(FATHER_NAME, fatherName)
        editor.apply()
    }
    fun getFatherName(): String?{
        return prefs.getString(FATHER_NAME, null)
    }
    fun setUserDOB(userDOB: String){
        editor.putString(EMP_DOB, userDOB)
        editor.apply()
    }
    fun getUserDOB(): String?{
        return prefs.getString(EMP_DOB, null)
    }

fun setRemainingLeaves(remainingLeaves: Int){
    editor.putInt(REMAINING_LEAVES,remainingLeaves)
    editor.apply()
}
    fun getRemainingLeaves(): Int {
        return prefs.getInt(REMAINING_LEAVES,0)

    }

    fun setUsedLeaves(usedLeaves: Int){
        editor.putInt(USED_LEAVES,usedLeaves)
        editor.apply()
    }
    fun getUsedLeaves(): Int {
        return prefs.getInt(USED_LEAVES,0)

    }
    fun setUserNationality(userNationality: String){
        editor.putString(NATIONALITY, userNationality)
        editor.apply()
    }
    fun getUserNationality(): String?{
        return prefs.getString(NATIONALITY, null)
    }
    fun setUserGender(userGender: String){
        editor.putString(EMP_GENDER, userGender)
        editor.apply()
    }
    fun getUserGender(): String?{
        return prefs.getString(EMP_GENDER, null)
    }

    fun setUserAddress1(userAddress1: String){
        editor.putString(ADDRESS_1, userAddress1)
        editor.apply()
    }
    fun getUserAddress1(): String?{
        return prefs.getString(ADDRESS_1, null)
    }

    fun setUserAddress2(userAddress2: String){
        editor.putString(ADDRESS_2, userAddress2)
        editor.apply()
    }
    fun getUserAddress2(): String?{
        return prefs.getString(ADDRESS_2, null)
    }
    fun setUserCity(userCity: String){
        editor.putString(CITY, userCity)
        editor.apply()
    }
    fun getUserCity(): String?{
        return prefs.getString(CITY, null)
    }
    fun setUserCountry(userCountry: String){
        editor.putString(COUNTRY, userCountry)
        editor.apply()
    }
    fun getUserCountry(): String?{
        return prefs.getString(COUNTRY, null)
    }
    fun setUserState(userState: String){
        editor.putString(STATE, userState)
        editor.apply()
    }
    fun getUserState(): String?{
        return prefs.getString(STATE, null)
    }
    fun setUserBloodGrp(userBloodGrp: String){
        editor.putString(BLOOD_GRP, userBloodGrp)
        editor.apply()
    }
    fun getUserBloodGrp(): String?{
        return prefs.getString(BLOOD_GRP, null)
    }
    fun setUserAadhaarNo(userAadhaar: String){
        editor.putString(AADHAAR_NO, userAadhaar)
        editor.apply()
    }
    fun getUserAadhaarNo(): String?{
        return prefs.getString(AADHAAR_NO, null)
    }

    fun setUserPanNo(userPanNo: String){
        editor.putString(PAN_CARD, userPanNo)
        editor.apply()
    }
    fun getUserPanNo(): String?{
        return prefs.getString(PAN_CARD, null)
    }
}