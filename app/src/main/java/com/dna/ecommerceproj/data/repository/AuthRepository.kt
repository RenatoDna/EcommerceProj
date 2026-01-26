package com.dna.ecommerceproj.data.repository

import com.dna.ecommerceproj.data.model.UserLogin
import com.dna.ecommerceproj.data.model.UserRequest
import com.dna.ecommerceproj.data.network.ApiService

class AuthRepository(private val apiService: ApiService) {

    suspend fun login(userLogin: UserLogin) = apiService.login(userLogin)

    suspend fun register(userRequest: UserRequest) = apiService.register(userRequest)
}
