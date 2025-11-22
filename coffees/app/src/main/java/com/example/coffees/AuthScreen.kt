package com.example.coffees

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AuthScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_welcome)

        val btnSign = findViewById<Button>(R.id.btnSign)
        btnSign.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
            // Apply transition animations
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }
        val btnSignup = findViewById<Button>(R.id.btnSignup)
        btnSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            // Apply transition animations
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }
    }

    // Override onBackPressed to navigate back to WelcomeActivity
    override fun onBackPressed() {
        super.onBackPressed()
        // Start the WelcomeActivity
        val intent = Intent(this, NextActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()  // Optional: if you want to finish the current activity and prevent it from staying in the back stack
    }
}
