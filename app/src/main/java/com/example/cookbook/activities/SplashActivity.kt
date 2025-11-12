package com.example.cookbook.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import kotlin.jvm.java
import com.example.cookbook.R
import com.example.cookbook.helpers.LocaleHelper
import com.example.cookbook.helpers.SettingsManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Apply saved settings before showing UI
        SettingsManager.saveDarkMode(this, SettingsManager.isDarkModeEnabled(this))
        LocaleHelper.setLocale(this, SettingsManager.getLanguage(this))
        setContentView(R.layout.activity_splash)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        GlobalScope.launch {
            delay(2000)
            withContext(Dispatchers.Main) {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}