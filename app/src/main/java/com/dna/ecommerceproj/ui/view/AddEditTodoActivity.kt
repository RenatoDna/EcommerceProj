package com.dna.ecommerceproj.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dna.ecommerceproj.data.model.requestNote
import com.dna.ecommerceproj.data.network.RetrofitClient
import com.dna.ecommerceproj.databinding.ActivityAddEditTodoBinding
import com.dna.ecommerceproj.ui.viewModel.TodoViewModel
import com.dna.ecommerceproj.ui.viewModel.ViewModelFactory

class AddEditTodoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditTodoBinding
    private var currentTodoId: String? = null

    private val todoViewModel: TodoViewModel by viewModels {
        ViewModelFactory(RetrofitClient.apiService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.hasExtra(EXTRA_ID)) {
            supportActionBar?.title = "Editar Tarefa"
            currentTodoId = intent.getStringExtra(EXTRA_ID)
            val todoTitle = intent.getStringExtra(EXTRA_TITLE)
            val todoDescription = intent.getStringExtra(EXTRA_DESCRIPTION)
            val isCompleted = intent.getBooleanExtra(EXTRA_IS_COMPLETED, false)

            binding.etTodoTitle.setText(todoTitle)
            binding.etTodoDescription.setText(todoDescription)
            binding.statusIsCompleted.isChecked = isCompleted

        } else {
            supportActionBar?.title = "Adicionar Tarefa"
        }


        binding.btnSave.setOnClickListener {
            val title = binding.etTodoTitle.text.toString()
            val description = binding.etTodoDescription.text.toString()
            val isCompleted = binding.statusIsCompleted.isChecked

            if (title.isBlank()) {
                binding.etTodoTitle.error = "O título não pode estar vazio"
                return@setOnClickListener
            }

            if (description.isBlank()){
                binding.etTodoDescription.error = "A Descrição não pode esta vazia"
                return@setOnClickListener
            }

            if (currentTodoId == null) {
                todoViewModel.createTodo(title, description, isCompleted)
            } else {
                val updatedTodo = requestNote(title = title, description = description, isCompleted = isCompleted)
                todoViewModel.updateTodo(currentTodoId!!, updatedTodo)
            }
            val intent = Intent(this, TodoListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        binding.btnList.setOnClickListener {
            val intentLista = Intent(this, TodoListActivity::class.java)
            startActivity(intentLista)
        }
    }
    companion object {
        const val EXTRA_ID = "EXTRA_ID"
        const val EXTRA_TITLE = "EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"
        const val EXTRA_IS_COMPLETED = "EXTRA_IS_COMPLETED"
    }
}
