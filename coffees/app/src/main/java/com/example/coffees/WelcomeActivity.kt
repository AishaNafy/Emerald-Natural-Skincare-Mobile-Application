package com.example.coffees

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide() // Hide the action bar
        super.onCreate(savedInstanceState)

        // Check if the user is logged in
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            // If the user is logged in, redirect to the MainActivity (or Home Screen)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Finish WelcomeActivity to prevent the user from coming back
        } else {
            // If the user is not logged in, show the welcome screen
            setContentView(R.layout.activity_welcome)

            // Set up the "Next" button
            val btnNext = findViewById<Button>(R.id.btnNext)
            btnNext.setOnClickListener {
                val intent = Intent(this, NextActivity::class.java)
                startActivity(intent)
                // Apply transition animations
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }
        }
    }
}