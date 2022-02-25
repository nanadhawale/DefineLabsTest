package com.definelabs.definelabstest.activities.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.definelabs.definelabstest.R
import com.definelabs.definelabstest.activities.dashboard.DashboardActivity
import com.definelabs.definelabstest.network.RetrofitIClient

class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        },1500)
    }
}