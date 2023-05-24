package com.aisle.data.local.pref

interface Preference {

    fun setLogin()

    fun setMobileNumber(number : String)

    fun getMobileNumber() : String

    fun setToken(token : String)

    fun getToken() : String

    fun isLogin(): Boolean

    fun setCountryCode(code : String)

    fun getCountryCode() : String

    fun clear()
}
