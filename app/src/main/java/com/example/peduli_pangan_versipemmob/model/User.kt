package com.example.peduli_pangan_versipemmob.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val fullName: String = "",
    val phoneNumber: String = "",
    val address: String = "",
    val profileImageUrl: String = "",
    val role: UserRole = UserRole.CUSTOMER,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable

enum class UserRole {
    CUSTOMER, RESTAURANT_OWNER, ADMIN
}
