package com.example.seriesmanager

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seriesmanager.MainSerieActivity.Extras.EXTRA_SERIE
import com.example.seriesmanager.adapter.TemporadaRvAdapter
import com.example.seriesmanager.controller.TemporadaController
import com.example.seriesmanager.databinding.ActivityMainTemporadaBinding
import com.example.seriesmanager.model.Serie
import com.example.seriesmanager.model.Temporada
import com.google.android.material.snackbar.Snackbar

class MainTemporadaActivity : AppCompatActivity(), OnTemporadaClickListener {
    companion object Extras {
        const val EXTRA_TEMPORADA = "EXTRA_TEMPORADA"
        const val EXTRA_TEMPORADA_POSICAO = "EXTRA_SERIE_POSICAO"
        const val EXTRA_TEMPORADA_ID = "EXTRA_TEMPORADA_ID"
    }

    private lateinit var serie: Serie

    private val activityMainTemporadaBinding: ActivityMainTemporadaBinding by lazy { ActivityMainTemporadaBinding.inflate(layoutInflater) }
    private lateinit var temporadaActivityResultLauncher: ActivityResultLauncher<Intent>

    private val temporadaController: TemporadaController by lazy { TemporadaController(serie) }
    private val temporadaList: MutableList<Temporada> by lazy { temporadaController.buscarTemporadas(serie.nome) }
    private val temporadaAdapter: TemporadaRvAdapter by lazy { TemporadaRvAdapter(this, temporadaList) }
    private val temporadaLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainTemporadaBinding.root)

        serie = intent.getParcelableExtra<Serie>(EXTRA_SERIE)!!

        activityMainTemporadaBinding.TemporadasRv.adapter = temporadaAdapter
        activityMainTemporadaBinding.TemporadasRv.layoutManager = temporadaLayoutManager

        temporadaActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == RESULT_OK) {
                resultado.data?.getParcelableExtra<Temporada>(EXTRA_TEMPORADA)?.apply {
                    temporadaController.inserirTemporada(this)
                    temporadaList.add(this)
                    temporadaAdapter.notifyDataSetChanged()
                }
            }
        }



        activityMainTemporadaBinding.adicionarTemporadaFb.setOnClickListener {
            val addTemporadaIntent = Intent(this, TemporadaActivity::class.java)
            addTemporadaIntent.putExtra(EXTRA_SERIE, serie)
            temporadaActivityResultLauncher.launch(addTemporadaIntent)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val posicao = temporadaAdapter.posicao
        val temporada = temporadaList[posicao]

        return when(item.itemId) {
            R.id.removerTemporadaMi -> {
                with(AlertDialog.Builder(this)) {
                    setMessage("Confirmar remoção?")
                    setPositiveButton("Sim") { _, _ ->
                        temporadaController.apagarTemporadas(serie.nome, temporada.numeroSequencial)
                        temporadaList.removeAt(posicao)
                        temporadaAdapter.notifyDataSetChanged()
                        Snackbar.make(activityMainTemporadaBinding.root, "Temporada removida", Snackbar.LENGTH_SHORT).show()
                    }
                    setNegativeButton("Não") { _, _ ->
                        Snackbar.make(activityMainTemporadaBinding.root, "Remoção cancelada", Snackbar.LENGTH_SHORT).show()
                    }
                    create()
                }.show()

                true
            } else -> { false }
        }
    }
    override fun onTemporadaClick(posicao: Int) {
        val temporada = temporadaList[posicao]
        val temporadaId = temporadaController.buscarTemporadaId(temporada.nomeSerie, temporada.numeroSequencial)
        val consultarEpisodiosIntent = Intent(this, MainEpisodioActivity::class.java)
        consultarEpisodiosIntent.putExtra(EXTRA_TEMPORADA, temporada)
        consultarEpisodiosIntent.putExtra(EXTRA_TEMPORADA_ID, temporadaId)
        startActivity(consultarEpisodiosIntent)
    }
}