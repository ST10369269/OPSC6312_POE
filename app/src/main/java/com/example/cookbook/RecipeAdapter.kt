package com.example.cookbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.cookbook.databinding.ItemRecipeBinding
import com.bumptech.glide.Glide
import com.example.cookbook.Meal
import com.example.cookbook.R

class RecipeAdapter(private val meals: List<Meal>) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val recipeImage: ImageView = view.findViewById(R.id.recipeImage)
        val recipeTitle: TextView = view.findViewById(R.id.recipeTitle)
        val recipeDescription: TextView = view.findViewById(R.id.recipeDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val meal = meals[position]

        //Bind text
        holder.recipeTitle.text = meal.strMeal
        holder.recipeDescription.text = meal.strInstructions ?: "No instructions available"

        // Load image via Glide (placeholder should exist in res/drawable)
        Glide.with(holder.itemView.context)
            .load(meal.strMealThumb)
            .placeholder(R.drawable.placeholder)
            .into(holder.recipeImage)

        // Click -> open detail activity
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, RecipeDetailActivity::class.java)
            intent.putExtra("meal_name", meal.strMeal)
            intent.putExtra("meal_instructions", meal.strInstructions)
            intent.putExtra("meal_thumb", meal.strMealThumb)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = meals.size
}
