package com.example.peduli_pangan_versipemmob.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peduli_pangan_versipemmob.model.Category
import com.example.peduli_pangan_versipemmob.model.Food
import com.example.peduli_pangan_versipemmob.model.Restaurant
import com.example.peduli_pangan_versipemmob.repository.FirebaseRepository
import kotlinx.coroutines.launch

// Anotasi @Inject dan import javax.inject.Inject telah dihapus.
class HomeViewModel(
    private val repository: FirebaseRepository
) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> = _restaurants

    private val _foods = MutableLiveData<List<Food>>()
    val foods: LiveData<List<Food>> = _foods

    private val _discountedFoods = MutableLiveData<List<Food>>()
    val discountedFoods: LiveData<List<Food>> = _discountedFoods

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        _loading.value = true
        viewModelScope.launch {
            try {
                // Load categories
                repository.getAllCategories().fold(
                    onSuccess = { _categories.value = it },
                    onFailure = { _error.value = "Failed to load categories" }
                )

                // Load restaurants
                repository.getOpenRestaurants().fold(
                    onSuccess = { _restaurants.value = it },
                    onFailure = { _error.value = "Failed to load restaurants" }
                )

                // Load foods
                repository.getAllFoods().fold(
                    onSuccess = { _foods.value = it },
                    onFailure = { _error.value = "Failed to load foods" }
                )

                // Load discounted foods
                repository.getDiscountedFoods().fold(
                    onSuccess = { _discountedFoods.value = it },
                    onFailure = { _error.value = "Failed to load discounted foods" }
                )

            } finally {
                _loading.value = false
            }
        }
    }

    fun searchRestaurants(query: String) {
        viewModelScope.launch {
            repository.getAllRestaurants().fold(
                onSuccess = { allRestaurants ->
                    val filtered = allRestaurants.filter {
                        it.name.contains(query, ignoreCase = true) ||
                                it.categories.any { category -> category.contains(query, ignoreCase = true) }
                    }
                    _restaurants.value = filtered
                },
                onFailure = { _error.value = "Search failed" }
            )
        }
    }

    fun getFoodsByCategory(categoryId: String) {
        viewModelScope.launch {
            repository.getFoodsByCategory(categoryId).fold(
                onSuccess = { _foods.value = it },
                onFailure = { _error.value = "Failed to load foods by category" }
            )
        }
    }
}
