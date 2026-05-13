package com.example.arogyanidhi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val resultText = findViewById<TextView>(R.id.resultText)
        val hospitalBtn = findViewById<Button>(R.id.hospitalBtn)

        // GET DATA

        val income = intent.getStringExtra("income")?.toIntOrNull() ?: 0
        val family = intent.getStringExtra("family")?.toIntOrNull() ?: 0
        val job = intent.getStringExtra("job") ?: ""
        val cardType = intent.getStringExtra("cardType") ?: ""

        // PROFESSIONAL ELIGIBILITY LOGIC

        val result = if (

        // Must have BPL card
            cardType.contains("BPL") &&

            // Low income
            income <= 50000 &&

            // Large family
            family >= 4

        ) {
            "Eligible for Scheme"
        } else {
            "Not Eligible for Scheme"
        }

// SHOW RESULT
        resultText.text = result

        // OPEN HOSPITAL PAGE

        hospitalBtn.setOnClickListener {
            startActivity(Intent(this, HospitalActivity::class.java))
        }
    }
}