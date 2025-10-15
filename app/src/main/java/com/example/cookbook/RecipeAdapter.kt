package com.example.cookbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.cookbook.data.Meal
import com.example.cookbook.R

class RecipeAdapter(private val recipes: List<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeImage: ImageView = itemView.findViewById(R.id.recipeImage)
        val recipeTitle: TextView = itemView.findViewById(R.id.recipeTitle)
        val recipeDescription: TextView = itemView.findViewById(R.id.recipeDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]

        // <-- Use the exact property names from your Meal data class
        holder.recipeTitle.text = recipe.strMeal ?: "Untitled"
        holder.recipeDescription.text =
            (recipe.strInstructions?.take(120) ?: "No description available")

        // Load image with Glide. Use a valid drawable resource as placeholder.
        Glide.with(holder.itemView.context)
            .load(recipe.strMealThumb)                      // this must match your data class
            .placeholder(R.drawable.ic_launcher_background) // replace with a real drawable you have
            .error(R.drawable.ic_launcher_background)       // fallback
            .into(holder.recipeImage)

        // Click to open detail activity
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, RecipeDetailActivity::class.java).apply {
                putExtra("RECIPE_NAME", recipe.strMeal)
                putExtra("RECIPE_IMAGE", recipe.strMealThumb)
                putExtra("RECIPE_INSTRUCTIONS", recipe.strInstructions)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = recipes.size
}