package com.example.coffees

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.coffees.R

class SignupActivity : AppCompatActivity() {

    private lateinit var edtUsername: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtReenterPassword: EditText
    private lateinit var checkboxAgree: CheckBox
    private lateinit var btnSubmit: Button

    private lateinit var darkOverlay: View

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize the views
        edtUsername = findViewById(R.id.edtUsername)
        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        edtReenterPassword = findViewById(R.id.edtReenterPassword)
        checkboxAgree = findViewById(R.id.checkboxAgree)
        btnSubmit = findViewById(R.id.btnSubmit)
        darkOverlay = findViewById(R.id.darkOverlay)

        // Set the button click listener
        btnSubmit.setOnClickListener {
            validateForm()
        }
    }

    // Form validation function
    private fun validateForm() {
        val username = edtUsername.text.toString().trim()
        val email = edtEmail.text.toString().trim()
        val password = edtPassword.text.toString().trim()
        val reenterPassword = edtReenterPassword.text.toString().trim()

        // Validate fields
        if (username.isEmpty()) {
            edtUsername.error = "Username is required"
            return
        }
        if (email.isEmpty()) {
            edtEmail.error = "Email is required"
            return
        }
        if (password.isEmpty()) {
            edtPassword.error = "Password is required"
            return
        }
        if (reenterPassword.isEmpty()) {
            edtReenterPassword.error = "Please re-enter your password"
            return
        }
        if (password != reenterPassword) {
            edtReenterPassword.error = "Passwords do not match"
            return
        }
        if (!checkboxAgree.isChecked) {
            Toast.makeText(this, "You must agree to the terms and conditions", Toast.LENGTH_SHORT)
                .show()
            return
        }

        // If all fields are valid, show a success message
        Toast.makeText(this, "Sign Up Successful!", Toast.LENGTH_SHORT).show()

        // Proceed with the sign-up process (e.g., send data to the server)
        // Here you can call your API or save the data in a database
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
