package com.example.appasynctasksqlife

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setStatusBarColor()

        dbHelper = DatabaseHelper(this)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val usernameEditText = findViewById<EditText>(R.id.etUsername)
            val passwordEditText = findViewById<EditText>(R.id.etPassword)

            if (usernameEditText == null || passwordEditText == null) {
                Toast.makeText(this, "UI elements not found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                loginTask(username, password)
            }
        }
    }

    private fun setStatusBarColor() {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
    }

    private fun loginTask(username: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            val isValidUser = withContext(Dispatchers.IO) {
                dbHelper.validateUser(username, password)
            }

            if (isValidUser) {
                Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@LoginActivity, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
