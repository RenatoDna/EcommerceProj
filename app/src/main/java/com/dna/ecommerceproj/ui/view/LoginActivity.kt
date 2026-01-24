package com.dna.ecommerceproj.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dna.ecommerceproj.databinding.ActivityLoginBinding
import com.dna.ecommerceproj.databinding.ActivityMainBinding
import com.dna.ecommerceproj.ui.viewModel.AuthViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var bindingLogin: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindingLogin = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingLogin.root)

        setupClickListeners()
        observViewModel()

    }
    private fun setupClickListeners(){
        bindingLogin.btnLogin.setOnClickListener {
            val email = bindingLogin.etEmail.text.toString()
            val senha = bindingLogin.etPassword.text.toString()

            if(email.isNotEmpty() && senha.isNotEmpty()){
                bindingLogin.loading.visibility = View.VISIBLE
                viewModel.realizarLogin(email,senha)
            }else{
                Toast.makeText(this,"Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        bindingLogin.paginaCadastro.setOnClickListener {
            val intentCadastro = Intent(this, CadastroActivity::class.java)
            startActivity(intentCadastro)
        }

    }

    private fun observViewModel(){
        viewModel.loginResult.observe(this){ sucesso ->
            bindingLogin.loading.visibility = View.GONE
            if(sucesso){
                Toast.makeText(this,"Login com sucesso!",Toast.LENGTH_SHORT).show()

                val intent = Intent(this, ActivityMainBinding::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"Erro: Email ou senha incorretos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}