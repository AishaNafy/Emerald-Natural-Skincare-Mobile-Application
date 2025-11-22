package com.example.coffees

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class NextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.next_welcome)

        val btnNext = findViewById<Button>(R.id.btnNext)
        btnNext.setOnClickListener {
            val intent = Intent(this, AuthScreen::class.java)
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
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()  // Optional: if you want to finish the current activity and prevent it from staying in the back stack
    }
}
