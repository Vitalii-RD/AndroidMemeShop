package com.dudnyk.projectwithmaterialdesign.Preferences

import android.content.Context
import android.content.SharedPreferences
import com.dudnyk.projectwithmaterialdesign.Data.User
import com.dudnyk.projectwithmaterialdesign.R
import com.dudnyk.projectwithmaterialdesign.SQL.DatabaseHelper

class UserPreferences(context: Context) {
    private val sp: SharedPreferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
    private var db =  DatabaseHelper(context)

    companion object{
        const val USER_PREFERENCES = "USER_PREFERENCES"
        const val USER_ID = "USER_ID"
        const val USER_NAME = "USER_NAME"
        private var CURRENT_USER: User? = null
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

        CURRENT_USER = null
    }

    fun setCurrentUser(user: User) {
        sp.edit().apply {
            putInt(USER_ID, user.id).apply()
            putString(USER_NAME, user.name).apply()
        }.apply()

        CURRENT_USER = db.getUser(user.id)
    }

    fun getCurrentUser(default: User = User(-1, "Who am I?", "who@am.i", "whoami", R.drawable.unknown_user)): User {
        if (CURRENT_USER != null)
            return CURRENT_USER as User

        return db.getUser(getIntPreference(USER_ID)) ?: default
    }
}