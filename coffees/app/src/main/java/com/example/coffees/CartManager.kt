package com.example.coffees

import android.content.Context
import androidx.compose.runtime.mutableStateMapOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object CartManager {
    private val _cartItems = mutableStateMapOf<CoffeeItem, Int>()
    val cartItems: Map<CoffeeItem, Int> = _cartItems

    // Add to cart
    fun addToCart(coffee: CoffeeItem) {
        _cartItems[coffee] = (_cartItems[coffee] ?: 0) + 1
    }

    // Remove from cart
    fun removeFromCart(coffee: CoffeeItem) {
        val currentQuantity = _cartItems[coffee] ?: 0
        if (currentQuantity > 1) {
            _cartItems[coffee] = currentQuantity - 1
        } else {
            _cartItems.remove(coffee)
        }
    }

    // Remove item completely
    fun removeItemCompletely(coffee: CoffeeItem) {
        _cartItems.remove(coffee)
    }

    // Clear cart
    fun clearCart() {
        _cartItems.clear()
    }

    // Load cart items from SharedPreferences
    fun loadCartItems(context: Context) {
        val savedCartItems = SharedPreferencesHelper.getCartItems(context)
        _cartItems.clear()
        _cartItems.putAll(savedCartItems)
    }

    // Calculate total price
    fun getTotal(): Double {
        return cartItems.entries.sumOf { (coffee, quantity) ->
            coffee.price.removePrefix("$").toDouble() * quantity
        }
    }

    // Delivery fee
    fun getDeliveryFee(): Double = 2.0

    // Total with delivery
    fun getTotalWithDelivery(): Double = getTotal() + getDeliveryFee()
}