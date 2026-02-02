package com.dna.ecommerceproj.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dna.ecommerceproj.data.SessionManager
import com.dna.ecommerceproj.data.model.TodoItem
import com.dna.ecommerceproj.data.model.addNote
import com.dna.ecommerceproj.data.model.requestNote
import com.dna.ecommerceproj.data.model.resquestNoteStatus
import com.dna.ecommerceproj.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {

    private val _todos = MutableStateFlow<List<TodoItem>>(emptyList())
    val todos: StateFlow<List<TodoItem>> = _todos

    fun getTodos() {

        val userId = SessionManager.getCurrentUserId()
        if (userId == null) {
            return
        }

        viewModelScope.launch {
            val response = repository.getTodos(userId)
            if (response.isSuccessful) {
                _todos.value = response.body() ?: emptyList()
            }
        }
    }

    fun createTodo(title: String, description: String, isCompleted: Boolean) {
        val userId = SessionManager.getCurrentUserId()
        if (userId == null) {
            return
        }
        viewModelScope.launch {
            val newTodo = addNote(title = title, description = description, isCompleted = isCompleted, userId = userId)
            val response = repository.createTodo(newTodo)
            if(response.isSuccessful){
                getTodos()
            }
        }
    }

    fun updateTodo(id: String, note: requestNote) {
        viewModelScope.launch {
            repository.updateTodo(id,note)
            getTodos()
        }
    }

    fun updateTodoStatus(id: String,noteStatus: resquestNoteStatus){
        viewModelScope.launch {
            repository.updateTodoStatus(id,noteStatus)
        }
    }

    fun deleteTodo(id: String) {
        viewModelScope.launch {
            repository.deleteTodo(id)
            getTodos()
        }
    }
}
