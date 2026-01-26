package com.dna.ecommerceproj.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dna.ecommerceproj.data.model.UserLogin
import com.dna.ecommerceproj.data.network.RetrofitClient
import com.dna.ecommerceproj.databinding.ActivityLoginBinding
import com.dna.ecommerceproj.ui.viewModel.AuthViewModel
import com.dna.ecommerceproj.ui.viewModel.ViewModelFactory
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val authViewModel: AuthViewModel by viewModels {
        ViewModelFactory(RetrofitClient.apiService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            authViewModel.loginResult.collect { isSuccess ->
                if (isSuccess) {
                    Toast.makeText(this@LoginActivity, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, TodoListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        lifecycleScope.launch {
            authViewModel.error.collect { errorMessage ->
                if (errorMessage.isNotEmpty()) {
                    Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.login(UserLogin(email, password))
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.paginaCadastro.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }
}
