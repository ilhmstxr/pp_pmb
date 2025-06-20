package com.example.peduli_pangan_versipemmob.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val originalPrice: Int = 0,
    val discountPrice: Int? = null,
    val discountPercentage: Int = 0,
    val isAvailable: Boolean = true,
    val preparationTime: String = "15-20 min",
    val ingredients: String = "",
    val rating: Double = 0.0,
    val totalReviews: Int = 0,
    val restaurantId: String = "",
    val restaurantName: String = "",
    val categoryId: String = "",
    val categoryName: String = "",
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable
