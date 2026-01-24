package com.dna.ecommerceproj.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dna.ecommerceproj.data.model.UserLogin
import com.dna.ecommerceproj.data.model.UserRequest
import com.dna.ecommerceproj.data.network.RetrofitClient
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel(){
    val loginResult = MutableLiveData<Boolean>()
    val registerResult = MutableLiveData<Boolean>()

    fun realizarLogin(email: String,pass: String){
        viewModelScope.launch {
            try{
                val response = RetrofitClient.apiService.login(UserLogin(email,pass))
                loginResult.value = response.isSuccessful
            } catch (e: Exception){
                loginResult.postValue(false)
                Log.e("API ERROR","Falha na conexão: ${e.message}")
            }
        }
    }

    fun registrar (nome:String, sobrenome: String, email: String,pass: String){
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.register(UserRequest(nome,sobrenome,email,pass))
                registerResult.postValue(response.isSuccessful)
            }catch (e: Exception){
                registerResult.postValue(false)
                Log.e("API ERROR","Falha na conexão: ${e.message}")
            }
        }
    }

}