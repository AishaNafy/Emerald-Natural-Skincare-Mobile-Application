package com.example.coffees

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferencesHelper {

    private const val PREFS_NAME = "CoffeeAppPrefs"
    private const val KEY_CART_ITEMS = "cart_items"
    private const val KEY_IS_DELIVERY = "is_delivery"

    // Save cart items to SharedPreferences
    fun saveCartItems(context: Context, cartItems: Map<CoffeeItem, Int>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(cartItems)
        editor.putString(KEY_CART_ITEMS, json)
        editor.apply()
    }

    // Retrieve cart items from SharedPreferences
    fun getCartItems(context: Context): Map<CoffeeItem, Int> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString(KEY_CART_ITEMS, null)
        val type = object : TypeToken<Map<CoffeeItem, Int>>() {}.type
        return gson.fromJson(json, type) ?: emptyMap()
    }

    // Save delivery/pickup preference
    fun saveDeliveryPreference(context: Context, isDelivery: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean(KEY_IS_DELIVERY, isDelivery)
        editor.apply()
    }

    // Retrieve delivery/pickup preference
    fun getDeliveryPreference(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_IS_DELIVERY, true) // Default to delivery
    }
}