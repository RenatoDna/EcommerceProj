package com.dna.ecommerceproj.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dna.ecommerceproj.data.model.UserRequest
import com.dna.ecommerceproj.data.network.RetrofitClient
import com.dna.ecommerceproj.databinding.ActivityCadastroBinding
import com.dna.ecommerceproj.ui.viewModel.AuthViewModel
import com.dna.ecommerceproj.ui.viewModel.ViewModelFactory
import kotlinx.coroutines.launch

class CadastroActivity : AppCompatActivity() {

    private lateinit var bindingCadastro: ActivityCadastroBinding

    private val viewModel: AuthViewModel by viewModels {
        ViewModelFactory(RetrofitClient.apiService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingCadastro = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(bindingCadastro.root)

        bindingCadastro.btnCadastro.setOnClickListener {
            val nome = bindingCadastro.etRegNome.text.toString().trim()
            val sobrenome = bindingCadastro.etRegSobrenome.text.toString().trim()
            val email = bindingCadastro.etRegEmail.text.toString().trim()
            val senha = bindingCadastro.etRegPassword.text.toString().trim()

            if (nome.isNotEmpty() && sobrenome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()) {
                viewModel.register(UserRequest(nome, sobrenome, email, senha))
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
        lifecycleScope.launch {
            viewModel.registerResult.collect { sucesso ->
                if (sucesso) {
                    Toast.makeText(this@CadastroActivity, "Conta criada! Faça login", Toast.LENGTH_SHORT).show()
                    val intentLogin = Intent(this@CadastroActivity, LoginActivity::class.java)
                    startActivity(intentLogin)
                    finish()
                }
            }
        }


        lifecycleScope.launch {
            viewModel.error.collect { errorMsg ->
                if (errorMsg.isNotEmpty()) {
                    Toast.makeText(this@CadastroActivity, errorMsg, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
