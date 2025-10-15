package com.example.cookbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cookbook.R
import android.widget.ArrayAdapter
import com.example.cookbook.databinding.FragmentShoppingBinding

class ShoppingListFragment : Fragment() {
    private var _binding: FragmentShoppingBinding? = null
    private val binding get() = _binding!!
    private val items = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingBinding.inflate(inflater, container, false)
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
        binding.shoppingListView.adapter = adapter

        binding.addButton.setOnClickListener {
            val newItem = binding.itemInput.text.toString()
            if (newItem.isNotBlank()) {
                items.add(newItem)
                adapter.notifyDataSetChanged()
                binding.itemInput.text.clear()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}