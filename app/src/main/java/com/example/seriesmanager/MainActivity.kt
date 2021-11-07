package com.example.seriesmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.seriesmanager.adapter.SeriesAdapter
import com.example.seriesmanager.databinding.ActivityMainBinding
import com.example.seriesmanager.model.Serie

class MainActivity : AppCompatActivity() {
    companion object Extras{
        // const faz a variável ser estática o que permite utilizar em outra classe sem método de acesso activity.EXTRA_SERIE
        const val EXTRA_SERIE = "EXTRA_SERIE"
        const val EXTRA_POSICAO = "EXTRA_POSICAO"
    }
    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var serieActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var editarSerieActivityResultLauncher: ActivityResultLauncher<Intent>


    private val seriesList: MutableList<Serie> = mutableListOf()

    /* ADAPTER PADRÃO - Esse é um adapter pronto que utiliza um layout padrão simple_list_item_1
        private val seriesAdapter: ArrayAdapter<String> by lazy{
            val seriesStringList = mutableListOf<String>()
            seriesList.forEach { serie -> seriesStringList.add(serie.toString())}
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, seriesStringList)
        }
    */
    // ADAPTER CUSTOMIZADO
    private val seriesAdapter: SeriesAdapter by lazy{
        SeriesAdapter(this, R.layout.layout_serie, seriesList)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        inicializarSeriesList()

        //Associando o Adapter ao ListView
        activityMainBinding.listaSerieslv.adapter = seriesAdapter

        //Registrando o menu de contexto na View que queremos utilizá-lo
        registerForContextMenu(activityMainBinding.listaSerieslv)

        serieActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ resultado ->
            if(resultado.resultCode == RESULT_OK){ //"data" são os valores da Intent PaginaCadastro da Série
                resultado.data?.getParcelableExtra<Serie>(EXTRA_SERIE)?.apply {
                    seriesAdapter.add(this) // Adicionando no ListView
                    //Poderia também adicionar diretamente na fonte de dados e notificar o Adapter
                    //seriesList.add(this)
                    //seriesAdapter.notifyDataSetChanged()
                }
            }
        }

        editarSerieActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ resultado ->
            if(resultado.resultCode == RESULT_OK){
                val posicao = resultado.data?.getIntExtra(EXTRA_POSICAO, -1)
                resultado.data?.getParcelableExtra<Serie>(EXTRA_SERIE)?.apply {
                    /*
                        *Como em Serie.kt as propriedades foram definidas como 'val' e não 'var' não é possível editar aqui
                        * Então para realizar a atualização é preciso fazer uma substituição de célula na mesma posição
                        * Como o objeto a ser inserido é diferente do selecionado para edição, a posição não é a mesma
                        * portanto, quando selecionar a opção de seleção é preciso passar a informação da posição do item
                    */
                    if(posicao != null && posicao != -1){
                        seriesList [posicao] = this
                        seriesAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        //TRATANDO O CLICK
        activityMainBinding.listaSerieslv.setOnItemClickListener{adapterView, view, posicao, id ->
            //AQUI VAI REDIRECIONAR PARA PÁGINA TEMPORADAS
            val serie = seriesList[posicao]
            //val consultarTemporada = Intent(this, TemporadasActivity::class.java)
            //startActivity((consultarTemporada))
        }
    }

    private fun inicializarSeriesList(){
        for(indice in 1..10){
            seriesList.add(
                Serie(
                    "Série ${indice}",
                     "200${indice}",
                    "Emissora${indice}",
                    "Gênero ${indice}"
                )
            )
        }
    }

    /* Criação menu Action Bar*/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /* Função que trata quando uma opção do menu_main é clicada*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId){
        R.id.adicionarSerieMi -> {
            serieActivityResultLauncher.launch(Intent(this, SerieActivity::class.java))
            true
        }
        else -> {
            false
        }
    }

    /*CRIAÇÃO DO MENU DE CONTEXTO*/
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        /* O parâmetro 'item' não traz a posição do mesmo na lista onde estava
        * para conseguir a posição é preciso utilizar uma classe de contexto  chamada 'ContextMenuInfo' para fazer o casting
        * com o 'AdapterContextMenuInfo' para recuperar a posição do item do parâmetro
        */
        val posicao = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position

        return when (item.itemId){
            R.id.editarSerieMi -> {
                val serie = seriesList[posicao]
                val editarSerieIntent = Intent(this, SerieActivity::class.java)
                editarSerieIntent.putExtra(EXTRA_SERIE, serie)
                editarSerieIntent.putExtra(EXTRA_POSICAO, posicao)
                editarSerieActivityResultLauncher.launch(editarSerieIntent)
                true
            }
            R.id.excluirSerieMi -> {
                seriesList.removeAt(posicao)
                seriesAdapter.notifyDataSetChanged()
                true
            }
            else -> {false}
        }

    }


}