package com.example.shopaholics

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPref: SharedPreferences
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Enable Back button

        sharedPref = getSharedPreferences("ShopaholicsApp", MODE_PRIVATE)

        // Auto-login if user is already logged in
        if (sharedPref.getBoolean("is_logged_in", false)) {
            navigateToHome()
            return
        }

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            loginUser()
        }

        // Handle Back Press using OnBackPressedDispatcher
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish() // Close activity
            }
        })
    }

    private fun loginUser() {
        val enteredEmail = etEmail.text.toString().trim()
        val enteredPassword = etPassword.text.toString().trim()

        if (enteredEmail.isEmpty() || enteredPassword.isEmpty()) {
            Toast.makeText(this, "Please enter email and password!", Toast.LENGTH_SHORT).show()
            return
        }

        val savedEmail = sharedPref.getString("user_email", "")
        val savedPassword = sharedPref.getString("user_password", "")

        if (enteredEmail == savedEmail && enteredPassword == savedPassword) {
            sharedPref.edit().putBoolean("is_logged_in", true).apply()
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
            navigateToHome()
        } else {
            Toast.makeText(this, "Invalid Credentials!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
