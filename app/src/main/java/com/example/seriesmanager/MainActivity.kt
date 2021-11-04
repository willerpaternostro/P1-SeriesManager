package com.example.seriesmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.seriesmanager.databinding.ActivityMainBinding
import com.example.seriesmanager.model.Serie

class MainActivity : AppCompatActivity() {
    companion object Extras{
        // const faz a variável ser estática o que permite utilizar em outra classe sem método de acesso activity.EXTRA_SERIE
        const val EXTRA_SERIE = "EXTRA_SERIE"
    }
    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var serieActivityResultLauncher: ActivityResultLauncher<Intent>


    private val seriesList: MutableList<Serie> = mutableListOf()

    //ADAPTER
    private val seriesAdapter: ArrayAdapter<String> by lazy{
        val seriesStringList = mutableListOf<String>()
        seriesList.forEach { serie -> seriesStringList.add(serie.toString())}
        ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, seriesStringList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        inicializarSeriesList()

        //Associando o Adapter ao ListView
        activityMainBinding.listaSerieslv.adapter = seriesAdapter

        serieActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ resultado ->
            if(resultado.resultCode == RESULT_OK){
                //data são os valores da Intent página de cadastro da Série
                val serie = resultado.data?.getParcelableExtra<Serie>(EXTRA_SERIE)
                if(serie != null){
                    seriesList.add(serie) // Adicionando na fonte de dados
                    seriesAdapter.add(serie.toString()) // Adicionando no ListView
                }
            }
        }
    }

    private fun inicializarSeriesList(){
        for(indice in 1..10){
            seriesList.add(
                Serie(
                    "${indice}",
                     "${indice}",
                    "${indice}",
                    "${indice}"
                )
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId){
        R.id.adicionarSerieMi -> {
            serieActivityResultLauncher.launch(Intent(this, SerieActivity::class.java))
            true
        }
        else -> {
            false
        }
    }
}