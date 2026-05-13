package com.example.arogyanidhi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)

        // FIREBASE USER

        val user = FirebaseAuth.getInstance().currentUser

        // VIEWS

        val emailText =
            findViewById<TextView>(R.id.emailText)

        val uidText =
            findViewById<TextView>(R.id.uidText)

        val logoutBtn =
            findViewById<Button>(R.id.logoutBtn)

        val darkModeSwitch =
            findViewById<SwitchMaterial>(
                R.id.darkModeSwitch
            )

        // SHOW USER DETAILS

        emailText.text =
            "Email: ${user?.email}"

        uidText.text =
            "UID: ${user?.uid}"

        // LOGOUT BUTTON

        logoutBtn.setOnClickListener {

            FirebaseAuth.getInstance().signOut()

            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )

            finish()
        }

        // DARK MODE SWITCH

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {

                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )

            } else {

                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }
    }
}