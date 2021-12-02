package com.example.seriesmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.seriesmanager.databinding.ActivityAutenticacaoBinding

class AutenticacaoActivity : AppCompatActivity() {
    private val activityAutenticacaoBinding: ActivityAutenticacaoBinding by lazy {
        ActivityAutenticacaoBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityAutenticacaoBinding.root)
        supportActionBar?.subtitle = "Autenticação"

        with(activityAutenticacaoBinding){
            //Botão Cadatrar Usuário - Redirecionando para página de cadastro
            cadastrarUsuarioBt.setOnClickListener {
                startActivity(Intent(this@AutenticacaoActivity, CadastrarUsuarioActivity::class.java))
            }
            //Botão Recuperar Senha - Redirecionando para página de Recuperação
            recuperarSenhabt.setOnClickListener {
                startActivity(Intent(this@AutenticacaoActivity, RecuperarSenhaActivity::class.java))
            }

            // Botão Entrar
            entrarBt.setOnClickListener {
                val email = editTextEmail.text.toString()
                val senha = editTextSenha.text.toString()
                AutenticacaoFirebase.firebaseAuth.signInWithEmailAndPassword(email,senha).addOnSuccessListener {
                    Toast.makeText(this@AutenticacaoActivity, "Usuário autenticado", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@AutenticacaoActivity, MainSerieActivity::class.java))
                    finish()
                }.addOnFailureListener{
                    Toast.makeText(this@AutenticacaoActivity, "Usuário ou senha inválido",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


}