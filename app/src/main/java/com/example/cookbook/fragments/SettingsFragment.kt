package com.example.cookbook.fragments

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
import com.example.cookbook.activities.LoginActivity
import com.example.cookbook.databinding.FragmentSettingsBinding
import android.widget.ArrayAdapter
import android.widget.Toast

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        sharedPrefs = requireContext().getSharedPreferences("settings_prefs", 0)

        setupToolbar()
        setupDarkModeSwitch()
        setupNotificationSwitch()
        setupSpinner()
        setupLogoutButton()

        return view
        }

    private fun setupToolbar() {
        binding.toolbar.title = "CookBook"
    }

    private fun setupDarkModeSwitch() {
        val darkModeEnabled = sharedPrefs.getBoolean("dark_mode", false)
        binding.switchDarkMode.isChecked = darkModeEnabled

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            sharedPrefs.edit().putBoolean("dark_mode", isChecked).apply()
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Toast.makeText(requireContext(), "Dark mode enabled", Toast.LENGTH_SHORT).show()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Toast.makeText(requireContext(), "Dark mode disabled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupNotificationSwitch() {
        val notificationsEnabled = sharedPrefs.getBoolean("notifications", true)
        binding.switchNotifications.isChecked = notificationsEnabled

        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            sharedPrefs.edit().putBoolean("notifications", isChecked).apply()
            val message = if (isChecked) "Notifications enabled" else "Notifications disabled"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupSpinner() {
        val spinner: Spinner = binding.spinnerUnits

        // Populate spinner from resources
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.measurement_units,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        // Restore saved selection (default to first item)
        val savedUnit = sharedPrefs.getString("unit_pref", null)
        if (!savedUnit.isNullOrEmpty()) {
            val items = resources.getStringArray(R.array.measurement_units)
            val pos = items.indexOf(savedUnit)
            if (pos >= 0) spinner.setSelection(pos)
        }

        // Proper OnItemSelectedListener implementation
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedUnit = parent.getItemAtPosition(position).toString()
                sharedPrefs.edit().putString("unit_pref", selectedUnit).apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No-op
            }
        }
    }

    private fun setupLogoutButton() {
        binding.btnLogout.setOnClickListener {
            Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}