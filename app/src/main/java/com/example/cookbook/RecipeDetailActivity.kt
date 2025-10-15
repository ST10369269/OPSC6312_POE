package com.example.cookbook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cookbook.databinding.ActivityRecipeDetailBinding

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipeName = intent.getStringExtra("RECIPE_NAME")
        val recipeImage = intent.getStringExtra("RECIPE_IMAGE")
        val recipeInstructions = intent.getStringExtra("RECIPE_INSTRUCTIONS")

        binding.tvRecipeTitle.text = recipeName
        binding.tvRecipeInstructions.text = recipeInstructions

        Glide.with(this)
            .load(recipeImage)
            .into(binding.ivRecipeImage)

        binding.btnBack.setOnClickListener { finish() }
    }
}