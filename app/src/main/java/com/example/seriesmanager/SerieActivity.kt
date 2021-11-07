package com.example.seriesmanager

import android.content.Intent
import android.os.Bundle

import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.seriesmanager.databinding.ActivitySerieBinding
import com.example.seriesmanager.model.Serie

// ESTÁ ACTIVITY É USADA PARA CADASTRO E EDIÇÃO DE SÉRIE
class SerieActivity: AppCompatActivity() {
    private val activitySerieBinding: ActivitySerieBinding by lazy {
        ActivitySerieBinding.inflate(layoutInflater)
    }
    var posicao:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activitySerieBinding.root)

        //AÇÃO DE CLICK DO BOTÃO SALVAR
        activitySerieBinding.salvarSerieBt.setOnClickListener{ view:View  ->
            val serie = Serie(
                    activitySerieBinding.nomeSerieTv.text.toString(),
                    activitySerieBinding.anoLancamentoTv.text.toString(),
                    activitySerieBinding.emissoraTv.text.toString(),
                    activitySerieBinding.generoTv.text.toString()
            )
           val resultadoSerieIntent = Intent(this, MainActivity::class.java)
            resultadoSerieIntent.putExtra(MainActivity.EXTRA_SERIE,serie)
            if(posicao != -1){
                resultadoSerieIntent.putExtra(MainActivity.EXTRA_POSICAO, posicao)
            }
            setResult(RESULT_OK,resultadoSerieIntent)
            finish()
        }

        // VERIFICANDO SE É EDIÇÃO OU CADASTRO
        posicao = intent.getIntExtra(MainActivity.EXTRA_POSICAO,-1)
        val serie:Serie? = intent.getParcelableExtra<Serie>(MainActivity.EXTRA_SERIE)?.apply {
            if(posicao != -1){
                activitySerieBinding.nomeSerieTv.setText(this.nome)
                activitySerieBinding.anoLancamentoTv.setText(this.anoLancamento)
                activitySerieBinding.emissoraTv.setText(this.emissora)
                activitySerieBinding.generoTv.setText(this.genero)
            }
        }

    }
}