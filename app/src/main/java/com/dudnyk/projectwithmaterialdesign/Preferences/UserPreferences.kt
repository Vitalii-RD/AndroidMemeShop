package com.dudnyk.projectwithmaterialdesign.Preferences

import android.content.Context
import android.content.SharedPreferences
import com.dudnyk.projectwithmaterialdesign.Data.User

class UserPreferences(context: Context) {
    private var sp: SharedPreferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)

    companion object{
        const val USER_PREFERENCES = "USER_PREFERENCES"
        const val USER_ID = "USER_ID"
        const val USER_NAME = "USER_NAME"
    }

    fun getStringPreference(pref_name: String): String? {
        return sp.getString(pref_name, "")
    }

    fun getIntPreference(pref_name: String): Int {
        return sp.getInt(pref_name, -1)
    }

    fun isLoggedIn(): Boolean {
        return getIntPreference(USER_ID) != -1
    }

    fun logOut(){
        sp.edit().apply {
            remove(USER_ID)
            remove(USER_NAME)
        }.apply()
    }

    fun setCurrentUser(user: User) {
        sp.edit().apply {
            putInt(USER_ID, user.id).apply()
            putString(USER_NAME, user.name).apply()
        }.apply()
    }

}