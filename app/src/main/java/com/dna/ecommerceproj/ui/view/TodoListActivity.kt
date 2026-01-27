package com.dna.ecommerceproj.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dna.ecommerceproj.data.SessionManager.getCurrentUserEmail
import com.dna.ecommerceproj.data.SessionManager.getCurrentUserName
import com.dna.ecommerceproj.data.network.RetrofitClient
import com.dna.ecommerceproj.databinding.ActivityTodoListBinding
import com.dna.ecommerceproj.ui.adapter.TodoAdapter
import com.dna.ecommerceproj.ui.viewModel.TodoViewModel
import com.dna.ecommerceproj.ui.viewModel.ViewModelFactory
import kotlinx.coroutines.launch

class TodoListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodoListBinding
    private lateinit var todoAdapter: TodoAdapter

    private val todoViewModel: TodoViewModel by viewModels {
        ViewModelFactory(RetrofitClient.apiService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        lifecycleScope.launch {
            todoViewModel.todos.collect { todos ->
                todoAdapter.updateData(todos)
            }
        }
        binding.nomeUser.text = getCurrentUserName()
        binding.emailUser.text = getCurrentUserEmail()

        binding.fabAddTodo.setOnClickListener {
            val intent = Intent(this, AddEditTodoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        todoViewModel.getTodos()
    }

    private fun setupRecyclerView() {
        todoAdapter = TodoAdapter(
            todos = emptyList(),
            onDeleteClick = { todoId ->
                todoViewModel.deleteTodo(todoId)
                Toast.makeText(this, "Tarefa deletada!", Toast.LENGTH_SHORT).show()
            },
            onEditClick = { todoItem ->
                val intent = Intent(this, AddEditTodoActivity::class.java).apply {
                    putExtra(AddEditTodoActivity.EXTRA_ID, todoItem.id)
                    putExtra(AddEditTodoActivity.EXTRA_TITLE, todoItem.title)
                    putExtra(AddEditTodoActivity.EXTRA_DESCRIPTION, todoItem.description)
                }
                startActivity(intent)
            }
        )

        binding.recyclerView.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(this@TodoListActivity)
        }
    }
}
