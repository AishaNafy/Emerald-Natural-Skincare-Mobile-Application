package com.example.coffees

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class SuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_success)

        // Initialize views
        val checkIcon = findViewById<ImageView>(R.id.checkIcon)
        val successText = findViewById<TextView>(R.id.successText)
        val beanContainer = findViewById<ConstraintLayout>(R.id.beanContainer)

        // Load and start animations
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)

        checkIcon.startAnimation(scaleUp)
        successText.startAnimation(fadeIn)

        // Add animated coffee beans
        for (i in 1..10) {
            val bean = ImageView(this).apply {
                setImageResource(R.drawable.coffee_bean)
                id = View.generateViewId()
                alpha = 0.3f
            }

            val params = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            beanContainer.addView(bean, params)

            // Randomly position beans
            bean.post {
                bean.x = (Math.random() * beanContainer.width).toFloat()
                bean.y = (Math.random() * beanContainer.height).toFloat()
                bean.rotation = (Math.random() * 360).toFloat()
            }
        }

        // Redirect to homepage after 3 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java) // Navigate to MainActivity which contains HomeScreen
            startActivity(intent)
            finish()
        }, 2000) // 3000 milliseconds = 3 seconds

    }
}
