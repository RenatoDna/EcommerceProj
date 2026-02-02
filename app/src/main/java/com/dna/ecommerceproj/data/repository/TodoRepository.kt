package com.dna.ecommerceproj.data.repository

import com.dna.ecommerceproj.data.model.addNote
import com.dna.ecommerceproj.data.model.requestNote
import com.dna.ecommerceproj.data.model.resquestNoteStatus
import com.dna.ecommerceproj.data.network.ApiService

class TodoRepository(private val apiService: ApiService) {

    suspend fun getTodos(userId: Int) = apiService.getTodos(userId)

    suspend fun createTodo(note: addNote) = apiService.createTodo(note)

    suspend fun updateTodo(id: String, note: requestNote) = apiService.updateTodo(id, note)

    suspend fun updateTodoStatus(id:String, noteStatus: resquestNoteStatus) = apiService.updateTodoStatus(id,noteStatus)
    suspend fun deleteTodo(id: String) = apiService.deleteTodo(id)

}
