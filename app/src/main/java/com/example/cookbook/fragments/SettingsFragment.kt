package com.example.cookbook.fragments

import android.Manifest
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.widget.Spinner
import androidx.appcompat.app.AppCompatDelegate
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.cookbook.R
import android.widget.Toast
import java.util.Locale
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment() {

    private lateinit var darkModeSwitch: Switch
    private lateinit var notificationsSwitch: Switch
    private lateinit var spinnerUnits: Spinner
    private lateinit var languageSpinner: Spinner
    private lateinit var logoutButton: Button
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Initialize shared preferences
        sharedPrefs = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        // Link UI components
        darkModeSwitch = view.findViewById(R.id.switchDarkMode)
        notificationsSwitch = view.findViewById(R.id.switchNotifications)
        spinnerUnits = view.findViewById(R.id.spinnerUnits)
        languageSpinner = view.findViewById(R.id.languageSpinner)
        logoutButton = view.findViewById(R.id.btnLogout)

        setupDarkMode()
        setupMeasurementUnits()
        setupLanguageSelector()
        setupLogoutButton()

        // Load the saved preference
        val isEnabled = sharedPrefs.getBoolean("notifications_enabled", false)
        notificationsSwitch.isChecked = isEnabled

        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPrefs.edit().putBoolean("notifications_enabled", isChecked).apply()

            if (isChecked) {
                requestNotificationPermission()
            }
        }

        return view
        }

    // Dark Mode toggle
    private fun setupDarkMode() {
        val isDark = sharedPrefs.getBoolean("dark_mode", false)
        darkModeSwitch.isChecked = isDark

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPrefs.edit().putBoolean("dark_mode", isChecked).apply()
            val mode = if (isChecked)
                AppCompatDelegate.MODE_NIGHT_YES
            else
                AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }

    // Notifications toggle (for demonstration)
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    100
                )
            }
        }
    }

    // Measurement Units spinner
    private fun setupMeasurementUnits() {
        val savedUnit = sharedPrefs.getString("unit_pref", "Metric")
        val units = resources.getStringArray(R.array.measurement_units)
        spinnerUnits.setSelection(units.indexOf(savedUnit))

        spinnerUnits.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                val selectedUnit = parent?.getItemAtPosition(position).toString()
                sharedPrefs.edit().putString("unit_pref", selectedUnit).apply()
                Toast.makeText(
                    requireContext(),
                    "Measurement units set to $selectedUnit",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }


    // Language selector spinner
    private fun setupLanguageSelector() {
        val languages = resources.getStringArray(R.array.language_options)
        val savedLangCode = sharedPrefs.getString("lang_pref", "en")

        val currentPos = when (savedLangCode) {
            "zu" -> languages.indexOf("isiZulu")
            "af" -> languages.indexOf("Afrikaans")
            else -> 0
        }
        languageSpinner.setSelection(currentPos)

        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                val selectedLanguage = languages[position]
                val langCode = when (selectedLanguage) {
                    "isiZulu" -> "zu"
                    "Afrikaans" -> "af"
                    else -> "en"
                }
                if (langCode != savedLangCode) {
                    setLocale(langCode)
                    sharedPrefs.edit().putString("lang_pref", langCode).apply()
                    requireActivity().recreate()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    // Apply the selected locale
    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        requireActivity().baseContext.resources.updateConfiguration(
            config,
            requireActivity().baseContext.resources.displayMetrics
        )
    }

    // Logout functionality
    private fun setupLogoutButton() {
        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(requireContext(), getString(R.string.logged_out), Toast.LENGTH_SHORT)
                .show()

            // Restart the app or redirect to login
            val intent = requireActivity().baseContext.packageManager
                .getLaunchIntentForPackage(requireActivity().baseContext.packageName)

            intent?.let {
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(it)
            } ?: run {
                // Optional: fallback if launch intent not found
                Toast.makeText(requireContext(), "Unable to restart app", Toast.LENGTH_SHORT).show()
            }
        }
    }
}