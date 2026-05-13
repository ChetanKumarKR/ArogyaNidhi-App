package com.example.arogyanidhi

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class AddHospitalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_hospital)

        // EDITTEXTS

        val hospitalNameEditText =
            findViewById<EditText>(R.id.hospitalNameEditText)

        val districtEditText =
            findViewById<EditText>(R.id.districtEditText)

        val locationEditText =
            findViewById<EditText>(R.id.locationEditText)

        val ratingEditText =
            findViewById<EditText>(R.id.ratingEditText)

        // BUTTON

        val saveHospitalBtn =
            findViewById<Button>(R.id.saveHospitalBtn)

        // FIREBASE

        val database = FirebaseDatabase.getInstance()

        val hospitalRef = database.getReference("hospitals")

        // SAVE BUTTON CLICK

        saveHospitalBtn.setOnClickListener {

            val name =
                hospitalNameEditText.text.toString().trim()

            val district =
                districtEditText.text.toString().trim()

            val location =
                locationEditText.text.toString().trim()

            val ratingText =
                ratingEditText.text.toString().trim()

            // VALIDATION

            if (
                name.isEmpty() ||
                district.isEmpty() ||
                location.isEmpty() ||
                ratingText.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val rating = ratingText.toFloat()

                // CREATE HOSPITAL OBJECT

                val hospital = Hospital(
                    name = name,
                    location = location,
                    rating = rating
                )

                // SAVE TO FIREBASE

                hospitalRef
                    .child(district)
                    .push()
                    .setValue(hospital)

                    .addOnSuccessListener {

                        Toast.makeText(
                            this,
                            "Hospital Added Successfully",
                            Toast.LENGTH_LONG
                        ).show()

                        // CLEAR FIELDS

                        hospitalNameEditText.setText("")
                        districtEditText.setText("")
                        locationEditText.setText("")
                        ratingEditText.setText("")
                    }

                    .addOnFailureListener {

                        Toast.makeText(
                            this,
                            "Failed to Add Hospital",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }
    }
}