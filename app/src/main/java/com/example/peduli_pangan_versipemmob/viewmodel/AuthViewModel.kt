package com.example.peduli_pangan_versipemmob.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peduli_pangan_versipemmob.model.User
import com.example.peduli_pangan_versipemmob.model.UserRole
import com.example.peduli_pangan_versipemmob.repository.FirebaseRepository
import kotlinx.coroutines.launch

// Anotasi @Inject dan import javax.inject.Inject telah dihapus.
class AuthViewModel(
    private val repository: FirebaseRepository
) : ViewModel() {

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    init {
        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        val userId = repository.getCurrentUserId()
        if (userId != null) {
            viewModelScope.launch {
                repository.getUserById(userId).fold(
                    onSuccess = { user ->
                        _currentUser.value = user
                        _authState.value = AuthState.Success("User logged in")
                    },
                    onFailure = {
                        _authState.value = AuthState.Error("Failed to get user data")
                    }
                )
            }
        }
    }

    fun register(
        username: String,
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String,
        address: String
    ) {
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Please fill all required fields")
            return
        }

        if (password.length < 6) {
            _authState.value = AuthState.Error("Password must be at least 6 characters")
            return
        }

        _authState.value = AuthState.Loading

        val user = User(
            username = username,
            email = email,
            fullName = fullName,
            phoneNumber = phoneNumber,
            address = address,
            role = UserRole.CUSTOMER
        )

        viewModelScope.launch {
            repository.registerUser(email, password, user).fold(
                onSuccess = { registeredUser ->
                    _currentUser.value = registeredUser
                    _authState.value = AuthState.Success("Registration successful")
                },
                onFailure = { exception ->
                    _authState.value = AuthState.Error(exception.message ?: "Registration failed")
                }
            )
        }
    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Please fill all fields")
            return
        }

        _authState.value = AuthState.Loading

        viewModelScope.launch {
            repository.loginUser(email, password).fold(
                onSuccess = { user ->
                    _currentUser.value = user
                    _authState.value = AuthState.Success("Login successful")
                },
                onFailure = { exception ->
                    _authState.value = AuthState.Error(exception.message ?: "Login failed")
                }
            )
        }
    }

    fun signOut() {
        repository.signOut()
        _currentUser.value = null
        _authState.value = AuthState.Success("Signed out")
    }

    sealed class AuthState {
        object Loading : AuthState()
        data class Success(val message: String) : AuthState()
        data class Error(val message: String) : AuthState()
    }
}
