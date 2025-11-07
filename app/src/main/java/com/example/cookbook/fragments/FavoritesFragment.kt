package com.example.cookbook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookbook.adapters.FavoritesAdapter
import com.example.cookbook.databinding.FragmentFavoritesBinding
import com.example.cookbook.helpers.NotificationHelper

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var sharedPreferences: android.content.SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("Favorites", Context.MODE_PRIVATE)

        val prefs = requireActivity().getSharedPreferences("CookBookPrefs", Context.MODE_PRIVATE)
        if (prefs.getBoolean("notifications_enabled", false)) {
            NotificationHelper.showNotification(requireContext(), "Recipe Saved!", "You added a recipe to your favorites.")
        }

        // Load favorites
        val favoriteRecipes = loadFavorites()

        favoritesAdapter = FavoritesAdapter(favoriteRecipes)
        binding.rvFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorites.adapter = favoritesAdapter

        binding.btnClearFavorites.setOnClickListener {
            clearFavorites()

        }

        return binding.root
    }

    private fun loadFavorites(): MutableList<FavoriteItem> {
        val favorites = mutableListOf<FavoriteItem>()
        val allEntries = sharedPreferences.all
        for ((key, value) in allEntries) {
            val parts = (value as String).split("|")
            if (parts.size >= 2) {
                favorites.add(FavoriteItem(key, parts[0], parts[1]))
            }
        }
        return favorites
    }

    private fun clearFavorites() {
        sharedPreferences.edit().clear().apply()
        favoritesAdapter.updateList(mutableListOf())
        Toast.makeText(requireContext(), "All favorites cleared", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class FavoriteItem(
    val title: String,
    val imageUrl: String,
    val mealInstructions: String
)