package com.example.arogyanidhi

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        // VIEWS

        val nameEditText =
            findViewById<EditText>(R.id.nameEditText)

        val emailEditText =
            findViewById<EditText>(R.id.emailEditText)

        val passwordEditText =
            findViewById<EditText>(R.id.passwordEditText)

        val createBtn =
            findViewById<Button>(R.id.createBtn)

        // CREATE ACCOUNT

        createBtn.setOnClickListener {

            val name =
                nameEditText.text.toString().trim()

            val email =
                emailEditText.text.toString().trim()

            val password =
                passwordEditText.text.toString().trim()

            // NAME VALIDATION

            if (name.isEmpty()) {

                nameEditText.error = "Enter your name"

                nameEditText.requestFocus()

                return@setOnClickListener
            }

            // EMAIL VALIDATION

            if (email.isEmpty()) {

                emailEditText.error = "Enter email"

                emailEditText.requestFocus()

                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                emailEditText.error = "Enter valid email"

                emailEditText.requestFocus()

                return@setOnClickListener
            }

            // PASSWORD VALIDATION

            if (password.isEmpty()) {

                passwordEditText.error = "Enter password"

                passwordEditText.requestFocus()

                return@setOnClickListener
            }

            if (password.length < 6) {

                passwordEditText.error =
                    "Password must be at least 6 characters"

                passwordEditText.requestFocus()

                return@setOnClickListener
            }

            // FIREBASE SIGNUP

            auth.createUserWithEmailAndPassword(
                email,
                password
            ).addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    Toast.makeText(
                        this,
                        "Account Created Successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(
                        Intent(
                            this,
                            QuizActivity::class.java
                        )
                    )

                    finish()

                } else {

                    Toast.makeText(
                        this,
                        task.exception?.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}