package com.example.peduli_pangan_versipemmob.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.peduli_pangan_versipemmob.adapter.CategoryAdapter
import com.example.peduli_pangan_versipemmob.adapter.FoodAdapter
import com.example.peduli_pangan_versipemmob.adapter.RestaurantAdapter
import com.example.peduli_pangan_versipemmob.databinding.FragmentHomeBinding
import com.example.peduli_pangan_versipemmob.model.Food
import com.example.peduli_pangan_versipemmob.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var restaurantAdapter: RestaurantAdapter
    private lateinit var foodAdapter: FoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        setupObservers()
        setupClickListeners()
    }

    private fun setupRecyclerViews() {
        // Categories RecyclerView
        categoryAdapter = CategoryAdapter { category ->
            viewModel.getFoodsByCategory(category.id)
            Toast.makeText(context, "Category: ${category.name}", Toast.LENGTH_SHORT).show()
        }

        binding.rvCategories.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(context, 4)
        }

        // Restaurants RecyclerView
        restaurantAdapter = RestaurantAdapter { restaurant ->
            // Navigate to restaurant detail
            Toast.makeText(context, "Restaurant: ${restaurant.name}", Toast.LENGTH_SHORT).show()
        }

        binding.rvRestaurants.apply {
            adapter = restaurantAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        // Foods RecyclerView
        foodAdapter = FoodAdapter(
            onFoodClick = { food ->
                // Navigate to food detail
                Toast.makeText(context, "Food: ${food.name}", Toast.LENGTH_SHORT).show()
            },
            onAddToCartClick = { food ->
                addToCart(food)
            }
        )

        binding.rvFoods.apply {
            adapter = foodAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupObservers() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categoryAdapter.submitList(categories)
        }

        viewModel.restaurants.observe(viewLifecycleOwner) { restaurants ->
            restaurantAdapter.submitList(restaurants)
        }

        viewModel.foods.observe(viewLifecycleOwner) { foods ->
            foodAdapter.submitList(foods)
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupClickListeners() {
        binding.tvSeeAllCategories.setOnClickListener {
            // Navigate to categories screen
        }

        binding.tvSeeAllRestaurants.setOnClickListener {
            // Navigate to restaurants screen
        }

        binding.tvSeeAllFoods.setOnClickListener {
            // Navigate to foods screen
        }

        binding.etSearch.setOnEditorActionListener { _, _, _ ->
            val query = binding.etSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                viewModel.searchRestaurants(query)
            }
            true
        }
    }

    private fun addToCart(food: Food) {
        // Add to cart logic
        Toast.makeText(context, "${food.name} ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
