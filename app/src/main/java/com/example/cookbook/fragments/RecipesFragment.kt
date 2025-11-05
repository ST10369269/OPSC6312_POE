package com.example.cookbook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookbook.databinding.FragmentRecipesBinding
import android.widget.Toast
import com.example.cookbook.MealResponse
import com.example.cookbook.adapters.RecipeAdapter
import com.example.cookbook.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipesFragment : Fragment() {
    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.rvRecipes.layoutManager = LinearLayoutManager(requireContext())

        // Default search to "chicken"
        loadRecipes("chicken")

        // Search bar functionality
        binding.searchBar.setOnEditorActionListener { v, _, _ ->
            val query = v.text.toString()
            if (query.isNotEmpty()) {
                loadRecipes(query)
            }
            true
        }

        return binding.root
    }

    private fun loadRecipes(query: String) {
        RetrofitInstance.api.searchMeals(query).enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                val meals = response.body()?.meals
                if (meals != null) {
                    binding.rvRecipes.adapter = RecipeAdapter(meals)
                } else {
                    Toast.makeText(requireContext(), "No recipes found!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed to load recipes: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}