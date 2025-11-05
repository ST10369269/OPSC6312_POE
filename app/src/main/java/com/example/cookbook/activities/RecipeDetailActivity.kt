package com.example.cookbook.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cookbook.R
import com.example.cookbook.databinding.ActivityRecipeDetailBinding

class RecipeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeDetailBinding

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

        binding.detailTitle.text = mealName
        binding.detailInstructions.text = mealInstructions

        Glide.with(this)
            .load(mealThumb)
            .placeholder(R.drawable.placeholder)
            .into(binding.detailImage)

        binding.btnAddToShoppingList.setOnClickListener {
            // You’ll later handle saving ingredients to the shopping list (RoomDB)
        }
    }
}