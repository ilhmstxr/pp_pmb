package com.example.peduli_pangan_versipemmob.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Restaurant(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val address: String = "",
    val phoneNumber: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val openingTime: String = "",
    val closingTime: String = "",
    val isOpen: Boolean = true,
    val deliveryFee: Int = 0,
    val minOrder: Int = 0,
    val deliveryTime: String = "30-45 min",
    val rating: Double = 0.0,
    val totalReviews: Int = 0,
    val ownerId: String = "",
    val categories: List<String> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable
