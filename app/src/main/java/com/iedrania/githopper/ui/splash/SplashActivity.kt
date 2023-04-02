package com.iedrania.githopper.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.iedrania.githopper.R
import com.iedrania.githopper.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        val runnable = Runnable {
            startActivity(intent)
            finish()
        }
        findViewById<View>(android.R.id.content).postDelayed(runnable, 1000L)
    }
}