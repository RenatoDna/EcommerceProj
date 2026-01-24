package com.dna.ecommerceproj.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dna.ecommerceproj.databinding.ActivityCadastroBinding
import com.dna.ecommerceproj.ui.viewModel.AuthViewModel

class CadastroActivity : AppCompatActivity() {

    private lateinit var bindingCadastro: ActivityCadastroBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingCadastro = ActivityCadastroBinding.inflate(layoutInflater)

        setContentView(bindingCadastro.root)

        bindingCadastro.btnCadastro.setOnClickListener {
            val nome = bindingCadastro.etRegNome.text.toString()
            val sobrenome = bindingCadastro.etRegSobrenome.text.toString()
            val email = bindingCadastro.etRegEmail.text.toString()
            val password = bindingCadastro.etRegPassword.text.toString()

            if(nome.isNotEmpty() && sobrenome.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
                viewModel.registrar(nome,sobrenome,email,password)
            }else{
                Toast.makeText(this,"Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.registerResult.observe(this){sucesso ->
            if(sucesso){
                Toast.makeText(this,"Conta criada! Faça login", Toast.LENGTH_SHORT).show()
                val intentLogin = Intent(this, LoginActivity::class.java)
                startActivity(intentLogin)
            }else{
                Toast.makeText(this,"Erro ao cadastrar usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }
}