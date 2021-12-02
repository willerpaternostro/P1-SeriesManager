package com.example.seriesmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.seriesmanager.databinding.ActivityCadastrarUsuarioBinding

class CadastrarUsuarioActivity : AppCompatActivity() {
    private val activityCadastrarUsuarioBinding: ActivityCadastrarUsuarioBinding by lazy {
        ActivityCadastrarUsuarioBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityCadastrarUsuarioBinding.root)
        supportActionBar?.subtitle = "Cadastrar usuário"
        with(activityCadastrarUsuarioBinding){
            this.cadastrarBt.setOnClickListener{
                val email = editTextEmail.text.toString()
                val senha = editTextSenha.text.toString()
                val confirmarSenha = editTextConfirmarSenha.text.toString()

                if(senha == confirmarSenha){
                    AutenticacaoFirebase.firebaseAuth.createUserWithEmailAndPassword(email,senha).addOnSuccessListener {
                        Toast.makeText(this@CadastrarUsuarioActivity,"Usuário $email cadastrado", Toast.LENGTH_SHORT).show()
                        finish()
                    }.addOnFailureListener{
                        Toast.makeText(this@CadastrarUsuarioActivity, "Falha no Cadastro", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@CadastrarUsuarioActivity, "Senhas não coincidem", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}