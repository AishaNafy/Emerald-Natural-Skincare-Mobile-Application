package com.example.coffees

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coffees.R
import com.example.coffees.R.*

class SignIn : AppCompatActivity() {

    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()  // Hide the action bar
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_signin)

        // Initialize the views
        edtUsername = findViewById(id.edtUsername)
        edtPassword = findViewById(id.edtPassword)
        btnSubmit = findViewById(id.btnSubmit)

        // Set the button click listener
        btnSubmit.setOnClickListener {
            validateForm()
        }
    }

    // Form validation function
    private fun validateForm() {
        val username = edtUsername.text.toString().trim()
        val password = edtPassword.text.toString().trim()

        // Validate fields
        if (username.isEmpty()) {
            edtUsername.error = "Username is required"
            return
        }
        if (password.isEmpty()) {
            edtPassword.error = "Password is required"
            return
        }

        // If the fields are valid, show a success message
        Toast.makeText(this, "Sign In Successful!", Toast.LENGTH_SHORT).show()

        // Save login status in SharedPreferences
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("isLoggedIn", true)
            apply()
        }

        // Navigate to the MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()  // Finish the current activity to prevent the user from coming back to the sign-in page
    }

    // Override onBackPressed to navigate back to a different activity if needed
    override fun onBackPressed() {
        super.onBackPressed()
        // You can redirect to a different activity (e.g., WelcomeActivity)
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}