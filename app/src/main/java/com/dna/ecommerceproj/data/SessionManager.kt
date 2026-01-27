package com.dna.ecommerceproj.data

import com.dna.ecommerceproj.data.model.User

object SessionManager {

    var currentUser: User? = null

    fun getCurrentUserId(): Int? {
        return currentUser?.id
    }
    fun getCurrentUserEmail(): String? {
        return currentUser?.email
    }
    fun getCurrentUserName(): String? {
        return currentUser?.nome
    }

}
