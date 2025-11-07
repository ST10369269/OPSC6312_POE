package com.example.cookbook.activities

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cookbook.R
import com.example.cookbook.databinding.ActivityRecipeDetailBinding
import android.content.Intent
import android.widget.Toast
import com.example.cookbook.fragments.ShoppingListFragment

class RecipeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeDetailBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("Favorites", MODE_PRIVATE)

        // Toolbar back button
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val mealName = intent.getStringExtra("meal_name")
        val mealInstructions = intent.getStringExtra("meal_instructions")
        val mealThumb = intent.getStringExtra("meal_thumb")
        val imageUrl = intent.getStringExtra("imageUrl")

        binding.detailTitle.text = mealName
        binding.detailInstructions.text = mealInstructions

        Glide.with(this)
            .load(mealThumb)
            .placeholder(R.drawable.placeholder)
            .into(binding.detailImage)

        // Save to Favorites
        binding.btnSaveFavorite.setOnClickListener {
            saveToFavorites((title ?: "") as String, imageUrl ?: "", mealInstructions ?: "")
        }

        // Add to Shopping List
        binding.btnAddToShoppingList.setOnClickListener {
            val intent = Intent(this, ShoppingListFragment::class.java)
            intent.putExtra("recipeTitle", title)
            startActivity(intent)
        }
    }

    private fun saveToFavorites(title: String, imageUrl: String, mealInstructions: String) {
        val editor = sharedPreferences.edit()
        editor.putString(title, "$imageUrl|$mealInstructions")
        editor.apply()
        Toast.makeText(this, "$title added to favorites!", Toast.LENGTH_SHORT).show()
    }
}