package com.example.seriesmanager.adapter

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.seriesmanager.OnSerieClickListener
import com.example.seriesmanager.R
import com.example.seriesmanager.databinding.LayoutSerieBinding
import com.example.seriesmanager.model.Serie

class SerieRvAdapter(
    private val onSerieClickListener: OnSerieClickListener,
    private val seriesList: MutableList<Serie>
): RecyclerView.Adapter<SerieRvAdapter.SerieLayoutHolder>() {
    //Posição que será recuperada pelo menu de contexto
    var posicao: Int = -1
    //ViewHolder - Obrigatório em RecyclerView
    inner class  SerieLayoutHolder(layoutSerieBinding: LayoutSerieBinding): RecyclerView.ViewHolder(layoutSerieBinding.root), View.OnCreateContextMenuListener{
        val nomeTv: TextView = layoutSerieBinding.nomeLayoutTv
        val generoTv: TextView = layoutSerieBinding.generoLayoutTv
        val emissoraTv: TextView = layoutSerieBinding.emissoraLayoutTv
        val anoTv: TextView = layoutSerieBinding.anoLayoutTv
        //Executado logo após o construtor quando itemView já está disponível
        init {
            itemView.setOnCreateContextMenuListener(this)
        }
        override fun onCreateContextMenu(
                menu: ContextMenu?,
                view: View?,
                menuInfo: ContextMenu.ContextMenuInfo?) {
            MenuInflater(view?.context).inflate(R.menu.context_menu_main, menu)
        }
    }
    /*AS TRÊS FUNÇÕES SÃO OBRIGATÓRIAS */
    // É chamado quando uma nova célula precisa ser criada
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieLayoutHolder {
        //Criando uma nova célula
         val layoutSerieBinding = LayoutSerieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        //Criando um viewHolder e associando a uma nova célula
        return SerieLayoutHolder(layoutSerieBinding)
    }

    // É chamado quando for necessário a atualização  uma célula
    override fun onBindViewHolder(holder: SerieLayoutHolder, position: Int) {
       //Buscar a série
        val serie = seriesList[position]
        //Atualizando os valores de viewHolder
        with(holder){
            nomeTv.text = serie.nome
            generoTv.text = serie.genero
            emissoraTv.text = serie.emissora
            anoTv.text = serie.anoLancamento
        }
        //Click na célula - porém não é tratado o evento aqui. Ele é enviado para uma classe que implementa a interface OnSerieClickListener
        holder.itemView.setOnClickListener{onSerieClickListener.onSerieClick(position)}

        holder.itemView.setOnLongClickListener{
            posicao = position
            false //True significa que tratou aqui false significa que precisa ser tratada em outro lugar
        }

    }

    // Retorna a quantidade de itens que possui o data source atualmente
    override fun getItemCount(): Int = seriesList.size
}