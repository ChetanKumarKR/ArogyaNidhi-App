package com.example.arogyanidhi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        // DELAY 2 SECONDS

        Handler(Looper.getMainLooper()).postDelayed({

            // OPEN MAIN ACTIVITY

            startActivity(
                Intent(this, MainActivity::class.java)
            )

            finish()

        }, 2000)
    }
}