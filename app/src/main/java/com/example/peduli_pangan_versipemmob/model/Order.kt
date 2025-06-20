package com.example.peduli_pangan_versipemmob.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    val id: String = "",
    val orderNumber: String = "",
    val userId: String = "",
    val restaurantId: String = "",
    val restaurantName: String = "",
    val items: List<OrderItem> = emptyList(),
    val subtotal: Int = 0,
    val deliveryFee: Int = 0,
    val discountAmount: Int = 0,
    val totalAmount: Int = 0,
    val status: OrderStatus = OrderStatus.PENDING,
    val deliveryAddress: String = "",
    val deliveryNotes: String = "",
    val estimatedDeliveryTime: String = "",
    val orderDate: Long = System.currentTimeMillis(),
    val confirmedAt: Long? = null,
    val deliveredAt: Long? = null
) : Parcelable

@Parcelize
data class OrderItem(
    val foodId: String = "",
    val foodName: String = "",
    val foodImageUrl: String = "",
    val quantity: Int = 0,
    val unitPrice: Int = 0,
    val totalPrice: Int = 0,
    val notes: String = ""
) : Parcelable

enum class OrderStatus {
    PENDING, CONFIRMED, PREPARING, READY, ON_DELIVERY, DELIVERED, CANCELLED
}
