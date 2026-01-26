package com.dna.ecommerceproj.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dna.ecommerceproj.data.SessionManager
import com.dna.ecommerceproj.data.model.UserLogin
import com.dna.ecommerceproj.data.model.UserRequest
import com.dna.ecommerceproj.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _loginResult = MutableStateFlow(false)
    val loginResult: StateFlow<Boolean> = _loginResult

    private val _registerResult = MutableStateFlow(false)
    val registerResult: StateFlow<Boolean> = _registerResult

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    fun login(userLogin: UserLogin) {
        viewModelScope.launch {
            try {
                val response = repository.login(userLogin)
                if (response.isSuccessful && response.body() != null) {
                    SessionManager.currentUser = response.body()?.user
                    _loginResult.value = true
                } else {
                    _error.value = "Falha no login: ${response.message()}"
                    _loginResult.value = false
                }
            } catch (e: Exception) {
                _error.value = "Erro de conexão: ${e.message}"
                _loginResult.value = false
            }
        }
    }

    fun register(userRequest: UserRequest) {
        viewModelScope.launch {
            try {
                val response = repository.register(userRequest)
                if (response.isSuccessful) {
                    _registerResult.value = true
                } else {
                    _error.value = "Falha no registro: ${response.message()}"
                    _registerResult.value = false
                }
            } catch (e: Exception) {
                _error.value = "Erro de conexão: ${e.message}"
                _registerResult.value = false
            }
        }
    }
}
