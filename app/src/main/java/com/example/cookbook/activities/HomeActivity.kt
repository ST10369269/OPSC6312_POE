package com.example.cookbook.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.cookbook.fragments.HomeFragment
import com.example.cookbook.R
import com.example.cookbook.fragments.RecipesFragment
import com.example.cookbook.fragments.ShoppingListFragment
import com.example.cookbook.fragments.FavoritesFragment
import com.example.cookbook.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Check if user is not logged in
        if (auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        // Initialize bottom navigation
        bottomNavigation = findViewById(R.id.bottom_navigation)

        // Load the default fragment
        loadFragment(HomeFragment())
        bottomNavigation.selectedItemId = R.id.nav_home

        // Handle bottom navigation clicks
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_recipes -> loadFragment(RecipesFragment())
                R.id.nav_favorites -> loadFragment(FavoritesFragment())
                R.id.nav_shopping -> loadFragment(ShoppingListFragment())
                R.id.nav_settings -> loadFragment(SettingsFragment())
            }
            true
        }

        // Handle system back presses
        onBackPressedDispatcher.addCallback(this) {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.frame_layout)
            if (currentFragment !is HomeFragment) {
                // If not on home, go back to home
                loadFragment(HomeFragment())
                bottomNavigation.selectedItemId = R.id.nav_home
            } else {
                // Exit app if already on home
                finish()
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }
}