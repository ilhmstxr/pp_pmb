package com.example.peduli_pangan_versipemmob.utils

import com.example.peduli_pangan_versipemmob.model.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirebaseInitializer {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun initializeData() {
        try {
            initializeCategories()
            initializeRestaurants()
            initializeFoods()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun initializeCategories() {
        val categories = listOf(
            Category(
                id = "cat1",
                name = "Aneka Nasi",
                description = "Berbagai macam olahan nasi",
                iconUrl = "",
                isActive = true
            ),
            Category(
                id = "cat2",
                name = "Minuman",
                description = "Minuman segar dan hangat",
                iconUrl = "",
                isActive = true
            ),
            Category(
                id = "cat3",
                name = "Pizza",
                description = "Pizza dengan berbagai topping",
                iconUrl = "",
                isActive = true
            ),
            Category(
                id = "cat4",
                name = "Sandwich",
                description = "Sandwich dan burger",
                iconUrl = "",
                isActive = true
            ),
            Category(
                id = "cat5",
                name = "Dessert",
                description = "Makanan penutup dan kue",
                iconUrl = "",
                isActive = true
            ),
            Category(
                id = "cat6",
                name = "Salad",
                description = "Salad segar dan sehat",
                iconUrl = "",
                isActive = true
            ),
            Category(
                id = "cat7",
                name = "Soup",
                description = "Sup hangat dan berkuah",
                iconUrl = "",
                isActive = true
            ),
            Category(
                id = "cat8",
                name = "Jajanan",
                description = "Camilan dan makanan ringan",
                iconUrl = "",
                isActive = true
            )
        )

        categories.forEach { category ->
            firestore.collection("categories")
                .document(category.id)
                .set(category)
                .await()
        }
    }

    private suspend fun initializeRestaurants() {
        val restaurants = listOf(
            Restaurant(
                id = "rest1",
                name = "Mie Gacoan - Merr",
                description = "Restoran mie dengan cita rasa pedas yang menggugah selera",
                imageUrl = "",
                address = "Jl. Raya Merr No. 123, Surabaya",
                phoneNumber = "031-1234567",
                latitude = -7.2575,
                longitude = 112.7521,
                openingTime = "09:00",
                closingTime = "23:00",
                isOpen = true,
                deliveryFee = 5000,
                minOrder = 25000,
                deliveryTime = "25-35 min",
                rating = 4.8,
                totalReviews = 1250,
                ownerId = "owner1",
                categories = listOf("Mie", "Ayam")
            ),
            Restaurant(
                id = "rest2",
                name = "NASGURT Haji Sodlik",
                description = "Nasi gurih dengan berbagai lauk pilihan",
                imageUrl = "",
                address = "Jl. Haji Sodlik No. 45, Jakarta",
                phoneNumber = "021-9876543",
                latitude = -6.2088,
                longitude = 106.8456,
                openingTime = "08:00",
                closingTime = "22:00",
                isOpen = true,
                deliveryFee = 3000,
                minOrder = 20000,
                deliveryTime = "30-40 min",
                rating = 4.9,
                totalReviews = 890,
                ownerId = "owner2",
                categories = listOf("Nasi", "Ayam")
            ),
            Restaurant(
                id = "rest3",
                name = "Warung Padang Sederhana",
                description = "Masakan Padang autentik dengan bumbu tradisional",
                imageUrl = "",
                address = "Jl. Padang Raya No. 67, Jakarta",
                phoneNumber = "021-5555444",
                latitude = -6.2000,
                longitude = 106.8167,
                openingTime = "07:00",
                closingTime = "21:00",
                isOpen = false,
                deliveryFee = 4000,
                minOrder = 30000,
                deliveryTime = "20-30 min",
                rating = 4.7,
                totalReviews = 2100,
                ownerId = "owner1",
                categories = listOf("Padang", "Nasi")
            )
        )

        restaurants.forEach { restaurant ->
            firestore.collection("restaurants")
                .document(restaurant.id)
                .set(restaurant)
                .await()
        }
    }

    private suspend fun initializeFoods() {
        val foods = listOf(
            Food(
                id = "food1",
                name = "Nasi Goreng Jawa",
                description = "Nasi goreng dengan bumbu khas Jawa yang menggugah selera",
                imageUrl = "",
                originalPrice = 50000,
                discountPrice = 25000,
                discountPercentage = 50,
                isAvailable = true,
                preparationTime = "15-20 min",
                ingredients = "Nasi, Ayam, Telur, Kecap, Bumbu Jawa",
                rating = 4.9,
                totalReviews = 156,
                restaurantId = "rest2",
                restaurantName = "NASGURT Haji Sodlik",
                categoryId = "cat1",
                categoryName = "Aneka Nasi"
            ),
            Food(
                id = "food2",
                name = "Mie Ayam Bakso",
                description = "Mie ayam dengan bakso segar dan kuah yang gurih",
                imageUrl = "",
                originalPrice = 35000,
                discountPrice = 28000,
                discountPercentage = 20,
                isAvailable = true,
                preparationTime = "10-15 min",
                ingredients = "Mie, Ayam, Bakso, Sayuran, Kuah Kaldu",
                rating = 4.7,
                totalReviews = 89,
                restaurantId = "rest1",
                restaurantName = "Mie Gacoan - Merr",
                categoryId = "cat1",
                categoryName = "Aneka Nasi"
            ),
            Food(
                id = "food3",
                name = "Rendang Daging",
                description = "Rendang daging sapi empuk dengan bumbu rempah tradisional",
                imageUrl = "",
                originalPrice = 45000,
                discountPrice = 38000,
                discountPercentage = 15,
                isAvailable = false,
                preparationTime = "20-25 min",
                ingredients = "Daging Sapi, Santan, Cabai, Rempah-rempah",
                rating = 4.8,
                totalReviews = 234,
                restaurantId = "rest3",
                restaurantName = "Warung Padang Sederhana",
                categoryId = "cat1",
                categoryName = "Aneka Nasi"
            ),
            Food(
                id = "food4",
                name = "Es Teh Manis",
                description = "Es teh manis segar",
                imageUrl = "",
                originalPrice = 8000,
                discountPrice = null,
                discountPercentage = 0,
                isAvailable = true,
                preparationTime = "5 min",
                ingredients = "Teh, Gula, Es Batu",
                rating = 4.5,
                totalReviews = 45,
                restaurantId = "rest1",
                restaurantName = "Mie Gacoan - Merr",
                categoryId = "cat2",
                categoryName = "Minuman"
            ),
            Food(
                id = "food5",
                name = "Pizza Margherita",
                description = "Pizza klasik dengan tomat, mozzarella, dan basil",
                imageUrl = "",
                originalPrice = 85000,
                discountPrice = 68000,
                discountPercentage = 20,
                isAvailable = true,
                preparationTime = "25-30 min",
                ingredients = "Adonan Pizza, Tomat, Mozzarella, Basil",
                rating = 4.6,
                totalReviews = 78,
                restaurantId = "rest1",
                restaurantName = "Mie Gacoan - Merr",
                categoryId = "cat3",
                categoryName = "Pizza"
            )
        )

        foods.forEach { food ->
            firestore.collection("foods")
                .document(food.id)
                .set(food)
                .await()
        }
    }
}
