package com.example.cookbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cookbook.R
import android.widget.Toast
import com.example.cookbook.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        // Handle search
        binding.etSearch.setOnEditorActionListener { _, _, _ ->
            val query = binding.etSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                Toast.makeText(requireContext(), "Searching for: $query", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please enter a recipe name", Toast.LENGTH_SHORT).show()
            }
            true
        }

        // Handle category button clicks
        setupCategoryButtons()

        return view
    }

    private fun setupCategoryButtons() {
        binding.apply {
            // Each button triggers a small toast — can later navigate or filter
            val listener = View.OnClickListener { v ->
                val category = when (v.id) {
                    btnBreakfast.id -> "Breakfast"
                    btnLunch.id -> "Lunch"
                    btnDinner.id -> "Dinner"
                    btnDessert.id -> "Dessert"
                    else -> "Unknown"
                }
                Toast.makeText(requireContext(), "Filtering: $category recipes", Toast.LENGTH_SHORT).show()
            }

            btnBreakfast.setOnClickListener(listener)
            btnLunch.setOnClickListener(listener)
            btnDinner.setOnClickListener(listener)
            btnDessert.setOnClickListener(listener)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




