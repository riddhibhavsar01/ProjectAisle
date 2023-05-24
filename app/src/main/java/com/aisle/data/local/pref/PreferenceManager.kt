package com.aisle.data.local.pref

class PreferenceManager(private val sharedPreferences: EncPref) : Preference {

    companion object {
        private const val IS_LOGIN = "pref_is_login"
        private const val TOKEN = "token"
        private const val MOBILE_NUMBER = "mobile_number"
        private const val COUNTRY_CODE = "country_code"
    }

    override fun setLogin() {
        sharedPreferences.putBoolean(IS_LOGIN, true)
    }

    override fun setMobileNumber(number: String) {
        sharedPreferences.putString(MOBILE_NUMBER, number)
    }

    override fun getMobileNumber(): String {
        return sharedPreferences.getString(MOBILE_NUMBER, "")
    }

    override fun setToken(token: String) {
        sharedPreferences.putString(TOKEN, token)
    }

    override fun getToken(): String {
        return sharedPreferences.getString(TOKEN, "")
    }

    override fun isLogin(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGIN, false)
    }

    override fun setCountryCode(code: String) {
        sharedPreferences.putString(COUNTRY_CODE, code)
    }

    override fun getCountryCode(): String {
        return sharedPreferences.getString(COUNTRY_CODE, "+1")
    }

    override fun clear() {
        sharedPreferences.clear()
    }
}
