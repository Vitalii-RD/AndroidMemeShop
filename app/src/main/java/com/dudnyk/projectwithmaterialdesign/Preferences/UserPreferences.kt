package com.dudnyk.projectwithmaterialdesign.Preferences

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import com.dudnyk.projectwithmaterialdesign.Data.ShoppingCart
import com.dudnyk.projectwithmaterialdesign.Data.User
import com.dudnyk.projectwithmaterialdesign.R
import com.dudnyk.projectwithmaterialdesign.SQL.DatabaseHelper
import java.time.LocalDateTime
import java.util.*

class UserPreferences(context: Context) {
    private val userPref: SharedPreferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
    private val cartPref: SharedPreferences = context.getSharedPreferences(CARD_PREFERENCES, Context.MODE_PRIVATE)
    private var db =  DatabaseHelper(context)

    companion object{
        const val USER_PREFERENCES = "USER_PREFERENCES"
        const val USER_ID = "USER_ID"
        
        const val CARD_PREFERENCES = "CARD_PREFERENCES"
        const val CARD_ID = "CARD_ID"

        private var CURRENT_USER: User? = null
        private var CURRENT_CART: ShoppingCart? = null
        private var ORDERS: MutableList<ShoppingCart> = mutableListOf()
    }

    fun getStringPreference(pref_name: String): String? {
        return userPref.getString(pref_name, "")
    }

    fun getIntPreference(pref_name: String): Int {
        return userPref.getInt(pref_name, User.UNREGISTERED_USER_ID)
    }

    fun isLoggedIn(): Boolean {
        return getIntPreference(USER_ID) != User.UNREGISTERED_USER_ID
    }

    fun logOut(){
        userPref.edit().apply {
            remove(USER_ID)
        }.apply()

        CURRENT_USER = null
    }

    fun setCurrentUser(user: User) {
        CURRENT_USER = db.getUser(user.id)

        userPref.edit().apply {
            putInt(USER_ID, user.id).apply()
        }.apply()

        setNewCart()
        clearOrders()
    }

    private fun setNewCart() {
        val userId = getCurrentUser().id
        CURRENT_CART = ShoppingCart(userId)

        cartPref.edit().apply {
            putInt(CARD_ID, userId)
        }.apply()
    }

    fun getCurrentUser(default: User = User(User.UNREGISTERED_USER_ID, "Who am I?", "who@am.i", "whoami", R.drawable.unknown_user)): User {
        if (CURRENT_USER != null)
            return CURRENT_USER as User

        CURRENT_USER = db.getUser(getIntPreference(USER_ID))
        return CURRENT_USER ?: default
    }

    fun makeOrder() {
        CURRENT_CART!!.orderDate = Calendar.getInstance()
        ORDERS.add(CURRENT_CART!!)
        setNewCart()
    }

    fun getCurrentShoppingCart(): ShoppingCart {
        if (CURRENT_CART != null && CURRENT_CART!!.userId == getCurrentUser().id)
            return CURRENT_CART as ShoppingCart

//      TODO CURRENT_CART = db.getCart(getIntPreference(CART_ID))
        setNewCart()
        return CURRENT_CART as ShoppingCart
    }

    fun getOrders(): List<ShoppingCart> {
        return ORDERS
    }
    private fun clearOrders() {
        ORDERS = mutableListOf()
    }
}