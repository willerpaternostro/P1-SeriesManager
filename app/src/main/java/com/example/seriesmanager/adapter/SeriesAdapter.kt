package com.example.seriesmanager.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.seriesmanager.databinding.ActivitySerieBinding
import com.example.seriesmanager.model.Serie

class SeriesAdapter(
    val contexto: Context,
    val leiaute:Int,
    val listaSeries:MutableList<Serie>
    ): ArrayAdapter<Serie>(contexto,leiaute,listaSeries) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //convertView é a célula reciclada
        val serieLayoutView:View // Por uma limitação do Kotlin precisa criar uma instância
        if(convertView != null){
            serieLayoutView = convertView
        }
        else{
            //Infla uma célula nova
            val layoutSerieBinding = ActivitySerieBinding.inflate(
                contexto.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            )
            with(layoutSerieBinding){
                root.tag =  SerieLayoutHolder(nomeSerieTv, anoLancamentoTv, emissoraTv, generoTv)
                serieLayoutView = layoutSerieBinding.root
            }
        }
        val serie = listaSeries[position]

        val serieLayoutHolder = serieLayoutView.tag as SerieLayoutHolder
        with(serieLayoutHolder){
            nomeSerieTv.text = serie.nome
            anoTV.text = serie.anoLancamento
            emissoraTv.text = serie.emissora
            generoTv.text = serie.genero
        }

        return serieLayoutView
    }
    private data class  SerieLayoutHolder ( //Construtor da célula que será inflada
        val nomeSerieTv: TextView,
        val anoTV: TextView,
        val emissoraTv: TextView,
        val generoTv: TextView
        )

}