package com.example.cookbook.fragments

import android.Manifest
import android.annotation.SuppressLint
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
import com.google.android.material.switchmaterial.SwitchMaterial
import android.widget.Toast
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import com.example.cookbook.helpers.LocaleHelper
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.widget.SwitchCompat
import com.example.cookbook.activities.HomeActivity
import com.example.cookbook.helpers.SettingsManager
import java.util.Locale

class SettingsFragment : Fragment() {

    private lateinit var darkModeSwitch: SwitchCompat
    private lateinit var notificationsSwitch: SwitchCompat
    private lateinit var spinnerUnits: Spinner
    private lateinit var languageSpinner: Spinner
    private lateinit var logoutButton: Button
    private lateinit var sharedPrefs: SharedPreferences
    private var userChangingLang = false // prevent auto-trigger when loading

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Initialize shared preferences
        sharedPrefs = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)

        // Link UI components
        darkModeSwitch = view.findViewById(R.id.switchDarkMode)
        notificationsSwitch = view.findViewById(R.id.switchNotifications)
        spinnerUnits = view.findViewById(R.id.spinnerUnits)
        languageSpinner = view.findViewById(R.id.language_spinner)
        logoutButton = view.findViewById(R.id.btnLogout)

        setupMeasurementUnits()
        setupLogoutButton()

        //language options
        val languages = arrayOf("English", "Zulu")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        languageSpinner.adapter = adapter

        // Load the saved preference
        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            val mode = if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(mode)
            requireActivity().recreate()
        }

        // Notifications Toggle
        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPrefs.edit().putBoolean("notifications_enabled", isChecked).apply()
            if (isChecked) requestNotificationPermission()
        }

        // Handle Language spinner selection (only after user interacts)
        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (userChangingLang) {
                    val selectedLang = if (position == 0) "en" else "zu"
                    setLocale(selectedLang)
                } else {
                    userChangingLang = true
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        return view
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)
        requireActivity().resources.updateConfiguration(config, requireActivity().resources.displayMetrics)

        restartApp()
    }

    private fun restartApp() {
        val context = requireContext()
        val intent = Intent(context, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

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
                Toast.makeText(requireContext(), "Units set to $selectedUnit", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    // 🚪 Logout functionality
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