package com.example.peduli_pangan_versipemmob.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val iconUrl: String = "",
    val isActive: Boolean = true
) : Parcelable
