package com.example.seriesmanager

import android.content.Intent
import android.os.Bundle

import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.seriesmanager.MainSerieActivity.Extras.EXTRA_SERIE
import com.example.seriesmanager.MainSerieActivity.Extras.EXTRA_SERIE_POSICAO
import com.example.seriesmanager.controller.GeneroController
import com.example.seriesmanager.databinding.ActivitySerieBinding
import com.example.seriesmanager.model.Serie

class SerieActivity: AppCompatActivity() {

    private val activitySerieBinding: ActivitySerieBinding by lazy {
        ActivitySerieBinding.inflate(layoutInflater)
    }
    private var posicao = -1;
    private lateinit var serie: Serie

    // Controller
    private val generoController: GeneroController by lazy {
        GeneroController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activitySerieBinding.root)

        // Populando Sppiner
        val generos: MutableList<String> = generoController.buscarGeneros()
        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, generos)
        activitySerieBinding.generoSp.adapter = spinnerAdapter

        //Visualizar Série ou add um nova
        posicao = intent.getIntExtra(EXTRA_SERIE_POSICAO, -1)
        intent.getParcelableExtra<Serie>(EXTRA_SERIE)?.apply {
            activitySerieBinding.nomeEt.setText(this.nome)
            activitySerieBinding.anoLancamentoEt.setText(this.anoLancamento)
            activitySerieBinding.emissoraEt.setText(this.emissora)
            activitySerieBinding.generoSp.setSelection(spinnerAdapter.getPosition(this.genero))
            if (posicao != -1) {
                activitySerieBinding.nomeEt.isEnabled = false
                activitySerieBinding.anoLancamentoEt.isEnabled = false
                activitySerieBinding.emissoraEt.isEnabled = false
                activitySerieBinding.generoSp.isEnabled = false
                activitySerieBinding.salvarBt.visibility = View.GONE
            }
        }

        activitySerieBinding.salvarBt.setOnClickListener {
            serie = Serie(
                    activitySerieBinding.nomeEt.text.toString(),
                    activitySerieBinding.anoLancamentoEt.text.toString(),
                    activitySerieBinding.emissoraEt.text.toString(),
                    activitySerieBinding.generoSp.selectedItem.toString()
            )
            val resultadoIntent = intent.putExtra(EXTRA_SERIE, serie)
            if (posicao != -1) {
                resultadoIntent.putExtra(EXTRA_SERIE_POSICAO, posicao)
            }
            setResult(RESULT_OK, resultadoIntent)
            finish()
        }
    }
}