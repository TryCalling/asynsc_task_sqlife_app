package com.example.appasynctasksqlife

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Set the status bar color
        setStatusBarColor()

        dbHelper = DatabaseHelper(this)

        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        btnSignUp.setOnClickListener {
            val username = findViewById<EditText>(R.id.etUsername).text.toString().trim()
            val password = findViewById<EditText>(R.id.etPassword).text.toString().trim()
            val email = findViewById<EditText>(R.id.etEmail).text.toString().trim()
            val phone = findViewById<EditText>(R.id.etPhone).text.toString().trim()

            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidEmail(email)) {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidPhone(phone)) {
                Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val isInserted = dbHelper.insertUser(username, password, email, phone)
            if (isInserted) {
                Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Sign Up Failed. Username might already exist", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to set the status bar color
    private fun setStatusBarColor() {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.colorPrimaryDark) // Replace with your desired color
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPhone(phone: String): Boolean {
        return phone.length in 8..10 && phone.all { it.isDigit() }
    }
}

