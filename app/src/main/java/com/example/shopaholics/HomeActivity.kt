package com.example.shopaholics

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var tvWelcome: TextView
    private lateinit var btnLogout: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        sharedPref = getSharedPreferences("ShopaholicsApp", MODE_PRIVATE)

        tvWelcome = findViewById(R.id.tvWelcome)
        btnLogout = findViewById(R.id.btnLogout)
        recyclerView = findViewById(R.id.recyclerView)

        // Set welcome message
        val userName = sharedPref.getString("user_name", "User")
        tvWelcome.text = "Welcome, $userName!"

        // Setup RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val productList = listOf(
            Product("Smartphone", "₹15,000"),
            Product("Laptop", "₹50,000"),
            Product("Headphones", "₹2,500"),
            Product("Smartwatch", "₹5,000")
        )
        productAdapter = ProductAdapter(productList)
        recyclerView.adapter = productAdapter

        // Logout button click
        btnLogout.setOnClickListener {
            val editor = sharedPref.edit()
            editor.putBoolean("is_logged_in", false) // Only update login status
            editor.apply()

            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }
    }
}
