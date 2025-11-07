package com.example.cookbook.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookbook.databinding.ItemFavoriteBinding
import com.example.cookbook.fragments.FavoriteItem
import android.content.Intent
import com.example.cookbook.activities.RecipeDetailActivity

class FavoritesAdapter(private var favoriteList: MutableList<FavoriteItem>) :
    RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = favoriteList[position]

        // Bind text and image
        holder.binding.tvFavoriteTitle.text = item.title
        holder.binding.tvFavoriteInstructions.text = item.mealInstructions
        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .into(holder.binding.ivFavoriteImage)

        // Handle click → open RecipeDetailActivity
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, RecipeDetailActivity::class.java).apply {
                putExtra("strMeal", item.title)
                putExtra("strInstructions", item.mealInstructions)
                putExtra("strMealThumb", item.imageUrl)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = favoriteList.size

    fun updateList(newList: MutableList<FavoriteItem>) {
        favoriteList = newList
        notifyDataSetChanged()
    }
}