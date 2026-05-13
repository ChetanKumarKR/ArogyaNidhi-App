package com.example.arogyanidhi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RecommendationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_recommendation)

        // VIEWS

        val recommendationText =
            findViewById<TextView>(R.id.recommendationText)

        val continueBtn =
            findViewById<Button>(R.id.continueBtn)

        // GET DATA FROM QUIZ

        val cardType =
            intent.getStringExtra("cardType") ?: ""

        val occupation =
            intent.getStringExtra("occupation") ?: ""

        // AI RECOMMENDATION LOGIC

        val recommendation = when {

            // FARMER + BPL

            cardType.contains("BPL", true) &&
                    occupation.equals("Farmer", true) ->

                "Farmer Health Support Scheme"

            // LABOR + BPL

            cardType.contains("BPL", true) &&
                    occupation.equals("Labor Worker", true) ->

                "Labor Welfare Healthcare Scheme"

            // UNEMPLOYED + BPL

            cardType.contains("BPL", true) &&
                    occupation.equals("Unemployed", true) ->

                "Ayushman Bharat Healthcare Scheme"

            // APL USERS

            cardType.contains("APL", true) ->

                "Currently not eligible for free government healthcare schemes."

            // DEFAULT

            else ->

                "Basic Healthcare Support Scheme"
        }

        // SHOW RECOMMENDATION

        recommendationText.text = recommendation

        // CONTINUE BUTTON

        continueBtn.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    HospitalActivity::class.java
                )
            )
        }
    }
}