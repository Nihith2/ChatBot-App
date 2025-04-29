package com.example.chatbot


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.chatbot.ui.MainActivity

class SplashActivity : AppCompatActivity() {

    private val splashScreenDuration = 3000L // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            // After the delay, navigate to the main activity (your chat screen)
           // val intent = Intent(this@SplashActivity, MainActivity::class.java)
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish() // Close splash screen so user cannot return to it
        }, splashScreenDuration)
    }
}
