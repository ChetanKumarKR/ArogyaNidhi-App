package com.example.arogyanidhi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        // AUTO LOGIN

        if (auth.currentUser != null) {

            startActivity(
                Intent(this, QuizActivity::class.java)
            )

            finish()

            return
        }

        // VIEWS

        val emailEditText =
            findViewById<EditText>(R.id.emailEditText)

        val passwordEditText =
            findViewById<EditText>(R.id.passwordEditText)

        val loginBtn =
            findViewById<Button>(R.id.loginBtn)

        val createAccountBtn =
            findViewById<Button>(R.id.createAccountBtn)

        val progressBar =
            findViewById<ProgressBar>(R.id.progressBar)

        // LOGIN BUTTON

        loginBtn.setOnClickListener {

            val email =
                emailEditText.text.toString().trim()

            val password =
                passwordEditText.text.toString().trim()

            // VALIDATION

            if (email.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    this,
                    "Enter email and password",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // SHOW LOADING

            progressBar.visibility = View.VISIBLE

            loginBtn.isEnabled = false

            // FIREBASE LOGIN

            auth.signInWithEmailAndPassword(
                email,
                password
            ).addOnCompleteListener { task ->

                // HIDE LOADING

                progressBar.visibility = View.GONE

                loginBtn.isEnabled = true

                if (task.isSuccessful) {

                    Toast.makeText(
                        this,
                        "Login Successful",
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
                        "Invalid Email or Password",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        // CREATE ACCOUNT

        createAccountBtn.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    SignupActivity::class.java
                )
            )
        }
    }
}