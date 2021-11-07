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
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seriesmanager.adapter.SerieRvAdapter
import com.example.seriesmanager.adapter.SeriesAdapter
import com.example.seriesmanager.databinding.ActivityMainBinding
import com.example.seriesmanager.model.Serie

/*PARA VINCULAR O RECYCLERVIEW AQUI é PRECISO IMPLEMENTAR A INTERFACE OnSerieClickListener, assim como seu método onSerieClick*/
class MainActivity : AppCompatActivity(), OnSerieClickListener {
    companion object Extras{
        const val EXTRA_SERIE = "EXTRA_SERIE"
        const val EXTRA_POSICAO = "EXTRA_POSICAO"
    }

    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var serieActivityResultLauncher: ActivityResultLauncher<Intent>

    private lateinit var editarSerieActivityResultLauncher: ActivityResultLauncher<Intent>

    //Data source
    private val seriesList: MutableList<Serie> = mutableListOf()

    // ADAPTER CUSTOMIZADO RECYCLERVIEW
    private val seriesAdapter: SerieRvAdapter by lazy{
        SerieRvAdapter(this,seriesList)
    }
    /* No RecyclerView é necessário um LayoutManager*/
    private val  serieLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)
        //inicializarSeriesList()

        //Associando o Adapter E LayoutManager ao RecyclerView
        activityMainBinding.listaSeriesRv.adapter = seriesAdapter
        activityMainBinding.listaSeriesRv.layoutManager = serieLayoutManager

        serieActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ resultado ->
            if(resultado.resultCode == RESULT_OK){ //"data" são os valores da Intent PaginaCadastro da Série
                resultado.data?.getParcelableExtra<Serie>(EXTRA_SERIE)?.apply {
                    //  A adição no recyclerView não pode ser feito no adapter, apenas desse jeito
                    seriesList.add(this)
                    seriesAdapter.notifyDataSetChanged()
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

    override fun onContextItemSelected(item: MenuItem): Boolean {
        /* O parâmetro 'item' não traz a posição do mesmo na lista onde estava
        * para conseguir a posição é preciso utilizar uma classe de contexto  chamada 'ContextMenuInfo' para fazer o casting
        * com o 'AdapterContextMenuInfo' para recuperar a posição do item do parâmetro
        */
        val posicao = seriesAdapter.posicao

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

    override fun onSerieClick(posicao: Int) {
        //AQUI VAI REDIRECIONAR PARA PÁGINA TEMPORADAS
        val serie = seriesList[posicao]
        //val consultarTemporada = Intent(this, TemporadasActivity::class.java)
        //startActivity((consultarTemporada))
    }
}