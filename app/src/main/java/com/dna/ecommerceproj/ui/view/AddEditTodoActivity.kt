package com.dna.ecommerceproj.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
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
            val dataHora = intent.getStringExtra(DATA_HORA)

            binding.etTodoTitle.setText(todoTitle)
            binding.etTodoDescrition.setText(todoDescription)

            if(dataHora != null){
                binding.dataHora.text = "Criado em : $dataHora"
                binding.dataHora.visibility = View.VISIBLE
            }

        } else {
            supportActionBar?.title = "Adicionar Tarefa"
            binding.dataHora.visibility = View.GONE
        }

        binding.btnSave.setOnClickListener {
            val title = binding.etTodoTitle.text.toString()
            val description = binding.etTodoDescrition.text.toString()


            if (title.isBlank()) {
                binding.etTodoTitle.error = "O título não pode estar vazio"
                return@setOnClickListener
            }

            if (description.isBlank()){
                binding.etTodoDescrition.error = "A Descrição não pode esta vazia"
                return@setOnClickListener
            }

            if (currentTodoId == null) {

                todoViewModel.createTodo(title, description)
            } else {
                val updatedTodo = requestNote(title = title,description = description)
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
        const val DATA_HORA = "DATA_HORA"
    }
}

