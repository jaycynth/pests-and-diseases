package com.julie.adchakathon.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(context: Context) {

    private val PREF_NAME: String = "shared_pref"

    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, 0)
    
    fun setToken(token: String) {
        val editor = prefs.edit()
        editor.putString(Constants.TOKEN, token)
        editor.apply()
    }

    fun getToken(): String {
        return prefs.getString(Constants.TOKEN, null).toString()
    }


    fun setNames(names: String) {
        val editor = prefs.edit()
        editor.putString(Constants.NAMES, names)
        editor.apply()
    }

    fun getNames(): String {
        return prefs.getString(Constants.NAMES, null).toString()
    }
}
