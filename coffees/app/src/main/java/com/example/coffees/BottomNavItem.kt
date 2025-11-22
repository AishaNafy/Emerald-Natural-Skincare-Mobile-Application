package com.example.coffees

import androidx.annotation.DrawableRes

data class BottomNavItem(
    val route: String,
    @DrawableRes val icon: Int
)
