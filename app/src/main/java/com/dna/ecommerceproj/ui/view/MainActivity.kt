package com.dna.ecommerceproj.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dna.ecommerceproj.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGoToLogin.setOnClickListener {
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }

        binding.btnGoToCadastro.setOnClickListener {
            val intentCadastro = Intent(this, CadastroActivity::class.java)
            startActivity(intentCadastro)
        }
    }
}