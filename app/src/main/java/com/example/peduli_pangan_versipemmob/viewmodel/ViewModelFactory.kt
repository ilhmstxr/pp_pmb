package com.example.peduli_pangan_versipemmob.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.peduli_pangan_versipemmob.repository.FirebaseRepository

/**
 * Factory for creating ViewModels that require a FirebaseRepository.
 * This class manually provides the repository to the ViewModel's constructor.
 */
class ViewModelFactory(private val repository: FirebaseRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            // If the requested ViewModel is AuthViewModel, create it with the repository.
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(repository) as T
            }
            // If the requested ViewModel is HomeViewModel, create it with the repository.
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            // If the ViewModel is unknown, throw an exception.
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
