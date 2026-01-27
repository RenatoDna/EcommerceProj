package com.dna.ecommerceproj.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dna.ecommerceproj.data.model.TodoItem
import com.dna.ecommerceproj.databinding.ItemToDoBinding


class TodoAdapter(
    private var todos: List<TodoItem>,
    private val onDeleteClick: (String) -> Unit,
    private val onEditClick: (TodoItem) -> Unit,

) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemToDoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todos[position])
    }

    override fun getItemCount(): Int = todos.size
    fun updateData(newTodos: List<TodoItem>) {
        todos = newTodos
        notifyDataSetChanged()
    }

    inner class TodoViewHolder(private val binding: ItemToDoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: TodoItem) {
            binding.tituloTodo.text = todo.title
            binding.todoDescricao.text = todo.description
            binding.dataHora.text = todo.data_hora

            binding.editButton.setOnClickListener {
                onEditClick(todo)
            }
            binding.deleteButton.setOnClickListener {
                onDeleteClick(todo.id)
            }

        }
    }
}
