package com.dna.ecommerceproj.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dna.ecommerceproj.data.network.ApiService
import com.dna.ecommerceproj.data.repository.AuthRepository
import com.dna.ecommerceproj.data.repository.TodoRepository

class ViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(AuthRepository(apiService)) as T
        }
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(TodoRepository(apiService)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
