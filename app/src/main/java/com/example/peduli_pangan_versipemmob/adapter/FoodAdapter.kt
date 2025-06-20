package com.example.peduli_pangan_versipemmob.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.peduli_pangan_versipemmob.databinding.ItemFoodBinding
import com.example.peduli_pangan_versipemmob.model.Food
import java.text.NumberFormat
import java.util.*

class FoodAdapter(
    private val onFoodClick: (Food) -> Unit,
    private val onAddToCartClick: (Food) -> Unit
) : ListAdapter<Food, FoodAdapter.FoodViewHolder>(FoodDiffCallback()) {
    
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FoodViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class FoodViewHolder(
        private val binding: ItemFoodBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(food: Food) {
            binding.apply {
                tvFoodName.text = food.name
                tvFoodDescription.text = food.description
                tvRestaurantName.text = food.restaurantName
                tvRating.text = food.rating.toString()
                tvPreparationTime.text = food.preparationTime
                
                // Price handling
                val currentPrice = food.discountPrice ?: food.originalPrice
                tvCurrentPrice.text = formatCurrency(currentPrice)
                
                if (food.discountPrice != null) {
                    tvOriginalPrice.isVisible = true
                    tvDiscountBadge.isVisible = true
                    tvOriginalPrice.text = formatCurrency(food.originalPrice)
                    tvDiscountBadge.text = "${food.discountPercentage}% OFF"
                } else {
                    tvOriginalPrice.isVisible = false
                    tvDiscountBadge.isVisible = false
                }
                
                // Availability
                btnAddToCart.isEnabled = food.isAvailable
                btnAddToCart.text = if (food.isAvailable) "Tambah" else "Habis"
                
                if (food.imageUrl.isNotEmpty()) {
                    Glide.with(ivFoodImage.context)
                        .load(food.imageUrl)
                        .into(ivFoodImage)
                }
                
                root.setOnClickListener {
                    onFoodClick(food)
                }
                
                btnAddToCart.setOnClickListener {
                    if (food.isAvailable) {
                        onAddToCartClick(food)
                    }
                }
            }
        }
        
        private fun formatCurrency(amount: Int): String {
            return "Rp ${NumberFormat.getNumberInstance(Locale("id", "ID")).format(amount)}"
        }
    }
    
    private class FoodDiffCallback : DiffUtil.ItemCallback<Food>() {
        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem == newItem
        }
    }
}
