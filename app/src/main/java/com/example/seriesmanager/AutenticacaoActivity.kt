package com.example.seriesmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.seriesmanager.databinding.ActivityAutenticacaoBinding

class AutenticacaoActivity : AppCompatActivity() {
    private val activityAutenticacaoBinding: ActivityAutenticacaoBinding by lazy {
        ActivityAutenticacaoBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityAutenticacaoBinding.root)
        supportActionBar?.subtitle = "Autenticação"

        //Botão Cadatrar Usuário - Redirecionando para página de cadastro
        activityAutenticacaoBinding.cadastrarUsuarioBt.setOnClickListener {
            startActivity(Intent(this, CadastrarUsuarioActivity::class.java))
        }
        //Botão Recuperar Senha - Redirecionando para página de Recuperação
        activityAutenticacaoBinding.recuperarSenhabt.setOnClickListener {
            startActivity(Intent(this, RecuperarSenhaActivity::class.java))
        }
    }


}