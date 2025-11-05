package com.example.cookbook.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Button
import android.widget.Spinner
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import android.view.View
import android.widget.AdapterView
import com.example.cookbook.R

class SettingsActivity : AppCompatActivity() {

    private lateinit var switchDarkMode: Switch
    private lateinit var switchNotifications: Switch
    private lateinit var spinnerUnits: Spinner
    private lateinit var btnLogout: Button
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPrefs = getSharedPreferences("AppSettingsPrefs", MODE_PRIVATE)

        switchDarkMode = findViewById(R.id.switchDarkMode)
        switchNotifications = findViewById(R.id.switchNotifications)
        spinnerUnits = findViewById(R.id.spinnerUnits)
        btnLogout = findViewById(R.id.btnLogout)

        // Load preferences
        switchDarkMode.isChecked = sharedPrefs.getBoolean("dark_mode", false)
        switchNotifications.isChecked = sharedPrefs.getBoolean("notifications", true)
        spinnerUnits.setSelection(sharedPrefs.getInt("units", 0))

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
            sharedPrefs.edit().putBoolean("dark_mode", isChecked).apply()
        }

        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            sharedPrefs.edit().putBoolean("notifications", isChecked).apply()
        }

        spinnerUnits.setOnItemSelectedListener(SimpleSpinnerListener { position ->
            sharedPrefs.edit().putInt("units", position).apply()
        })

        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    class SimpleSpinnerListener(val onSelect: (Int) -> Unit) : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            onSelect(position)
        }
        override fun onNothingSelected(parent: AdapterView<*>) {}
    }
}