package com.example.peduli_pangan_versipemmob.repository

import com.example.peduli_pangan_versipemmob.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class
FirebaseRepository @Inject constructor() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    // Collections
    private val usersCollection = firestore.collection("users")
    private val restaurantsCollection = firestore.collection("restaurants")
    private val categoriesCollection = firestore.collection("categories")
    private val foodsCollection = firestore.collection("foods")
    private val ordersCollection = firestore.collection("orders")

    // Auth Methods
    suspend fun registerUser(email: String, password: String, user: User): Result<User> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: throw Exception("User ID is null")

            val userWithId = user.copy(id = userId)
            usersCollection.document(userId).set(userWithId).await()

            Result.success(userWithId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginUser(email: String, password: String): Result<User> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: throw Exception("User ID is null")

            val userDoc = usersCollection.document(userId).get().await()
            val user = userDoc.toObject(User::class.java) ?: throw Exception("User not found")

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getCurrentUserId(): String? = auth.currentUser?.uid

    fun signOut() = auth.signOut()

    // User Methods
    suspend fun getUserById(userId: String): Result<User> {
        return try {
            val userDoc = usersCollection.document(userId).get().await()
            val user = userDoc.toObject(User::class.java) ?: throw Exception("User not found")
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUser(user: User): Result<User> {
        return try {
            usersCollection.document(user.id).set(user).await()
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Restaurant Methods
    suspend fun getAllRestaurants(): Result<List<Restaurant>> {
        return try {
            val snapshot = restaurantsCollection
                .orderBy("rating", Query.Direction.DESCENDING)
                .get().await()
            val restaurants = snapshot.toObjects(Restaurant::class.java)
            Result.success(restaurants)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getOpenRestaurants(): Result<List<Restaurant>> {
        return try {
            val snapshot = restaurantsCollection
                .whereEqualTo("isOpen", true)
                .orderBy("rating", Query.Direction.DESCENDING)
                .get().await()
            val restaurants = snapshot.toObjects(Restaurant::class.java)
            Result.success(restaurants)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getRestaurantById(restaurantId: String): Result<Restaurant> {
        return try {
            val doc = restaurantsCollection.document(restaurantId).get().await()
            val restaurant = doc.toObject(Restaurant::class.java) ?: throw Exception("Restaurant not found")
            Result.success(restaurant)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createRestaurant(restaurant: Restaurant): Result<Restaurant> {
        return try {
            val docRef = restaurantsCollection.document()
            val restaurantWithId = restaurant.copy(id = docRef.id)
            docRef.set(restaurantWithId).await()
            Result.success(restaurantWithId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Category Methods
    suspend fun getAllCategories(): Result<List<Category>> {
        return try {
            val snapshot = categoriesCollection
                .whereEqualTo("isActive", true)
                .get().await()
            val categories = snapshot.toObjects(Category::class.java)
            Result.success(categories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Food Methods
    suspend fun getAllFoods(): Result<List<Food>> {
        return try {
            val snapshot = foodsCollection
                .whereEqualTo("isAvailable", true)
                .orderBy("rating", Query.Direction.DESCENDING)
                .get().await()
            val foods = snapshot.toObjects(Food::class.java)
            Result.success(foods)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFoodsByRestaurant(restaurantId: String): Result<List<Food>> {
        return try {
            val snapshot = foodsCollection
                .whereEqualTo("restaurantId", restaurantId)
                .whereEqualTo("isAvailable", true)
                .get().await()
            val foods = snapshot.toObjects(Food::class.java)
            Result.success(foods)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFoodsByCategory(categoryId: String): Result<List<Food>> {
        return try {
            val snapshot = foodsCollection
                .whereEqualTo("categoryId", categoryId)
                .whereEqualTo("isAvailable", true)
                .get().await()
            val foods = snapshot.toObjects(Food::class.java)
            Result.success(foods)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getDiscountedFoods(): Result<List<Food>> {
        return try {
            val snapshot = foodsCollection
                .whereGreaterThan("discountPercentage", 0)
                .whereEqualTo("isAvailable", true)
                .orderBy("discountPercentage", Query.Direction.DESCENDING)
                .get().await()
            val foods = snapshot.toObjects(Food::class.java)
            Result.success(foods)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFoodById(foodId: String): Result<Food> {
        return try {
            val doc = foodsCollection.document(foodId).get().await()
            val food = doc.toObject(Food::class.java) ?: throw Exception("Food not found")
            Result.success(food)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Order Methods
    suspend fun createOrder(order: Order): Result<Order> {
        return try {
            val docRef = ordersCollection.document()
            val orderWithId = order.copy(id = docRef.id)
            docRef.set(orderWithId).await()
            Result.success(orderWithId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getOrdersByUser(userId: String): Result<List<Order>> {
        return try {
            val snapshot = ordersCollection
                .whereEqualTo("userId", userId)
                .orderBy("orderDate", Query.Direction.DESCENDING)
                .get().await()
            val orders = snapshot.toObjects(Order::class.java)
            Result.success(orders)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getOrderById(orderId: String): Result<Order> {
        return try {
            val doc = ordersCollection.document(orderId).get().await()
            val order = doc.toObject(Order::class.java) ?: throw Exception("Order not found")
            Result.success(order)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateOrderStatus(orderId: String, status: OrderStatus): Result<Unit> {
        return try {
            val updates = mutableMapOf<String, Any>("status" to status.name)

            when (status) {
                OrderStatus.CONFIRMED -> updates["confirmedAt"] = System.currentTimeMillis()
                OrderStatus.DELIVERED -> updates["deliveredAt"] = System.currentTimeMillis()
                else -> {}
            }

            ordersCollection.document(orderId).update(updates).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
