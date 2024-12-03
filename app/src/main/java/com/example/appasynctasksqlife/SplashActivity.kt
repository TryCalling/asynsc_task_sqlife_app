package com.example.appasynctasksqlife

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView

class SplashActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        progressBar = findViewById(R.id.horizontalProgressBar)
        progressText = findViewById(R.id.progressText)

        // Set the status bar color
        setStatusBarColor()

        // Simulate loading progress
        simulateProgress()
    }

    // Function to set the status bar color
    private fun setStatusBarColor() {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.colorPrimaryDark) // Replace with your desired color
    }

    private fun simulateProgress() {
        val handler = Handler(Looper.getMainLooper())
        var progress = 0

        handler.postDelayed(object : Runnable {
            override fun run() {
                if (progress <= 100) {
                    progressBar.progress = progress
                    progressText.text = "Loading... $progress%"
                    progress += 10
                    handler.postDelayed(this, 500) // Update every 500ms
                } else {
                    // When loading is complete, move to the next screen
                    val intent = Intent(this@SplashActivity, SignupActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, 500)
    }

}
