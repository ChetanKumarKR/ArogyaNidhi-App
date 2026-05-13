package com.example.arogyanidhi

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_quiz)

        // INPUT FIELDS

        val incomeEditText =
            findViewById<EditText>(R.id.incomeEditText)

        val familySizeEditText =
            findViewById<EditText>(R.id.familySizeEditText)

        val jobTypeEditText =
            findViewById<EditText>(R.id.jobTypeEditText)

        // RADIO BUTTONS

        val aplRadio =
            findViewById<RadioButton>(R.id.aplRadio)

        val bplRadio =
            findViewById<RadioButton>(R.id.bplRadio)

        // BUTTON

        val checkBtn =
            findViewById<Button>(R.id.checkBtn)

        checkBtn.setOnClickListener {

            val income =
                incomeEditText.text.toString().trim()

            val family =
                familySizeEditText.text.toString().trim()

            val job =
                jobTypeEditText.text.toString().trim()

            // CARD TYPE

            val cardType = when {

                aplRadio.isChecked -> "APL"

                bplRadio.isChecked -> "BPL"

                else -> ""
            }

            // VALIDATION

            if (
                income.isEmpty() ||
                family.isEmpty() ||
                job.isEmpty() ||
                cardType.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Please fill all details",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                // GO TO AI RECOMMENDATION SCREEN

                val intent = Intent(
                    this,
                    RecommendationActivity::class.java
                )

                // SEND DATA

                intent.putExtra(
                    "income",
                    income
                )

                intent.putExtra(
                    "family",
                    family
                )

                intent.putExtra(
                    "occupation",
                    job
                )

                intent.putExtra(
                    "cardType",
                    cardType
                )

                startActivity(intent)
            }
        }
    }
}