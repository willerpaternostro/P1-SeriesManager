package com.example.seriesmanager.model

import com.example.seriesmanager.dao.SerieDao
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SerieFirebase: SerieDao {
    companion object {
        private val BD_SERIES = "series" // Nó Raiz
    }
    //Referência para Real Time Database RTDB -> series
    //Se não existir esse nó BD_SERIES ele cria
    private val seriesRTDB = Firebase.database.getReference(BD_SERIES)

    //
    private val serieList: MutableList<Serie> = mutableListOf()
    init{
        seriesRTDB.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                //Quando um nó é adicionado
                val novaSerie: Serie? = snapshot.value as? Serie
                novaSerie?.apply {
                    if(serieList.find { it.nome == this.nome } == null){
                        serieList.add(this)
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                //Quando um nó foi modificado
                val serieEditada: Serie? = snapshot.value as? Serie
                serieEditada?.apply {
                    serieList[serieList.indexOfFirst { it.nome == this.nome}] = this
                }
            }
            override fun onChildRemoved(snapshot: DataSnapshot) {
                //Quando um nó foi removido
                val serieRemovida: Serie? = snapshot.value as? Serie
                serieRemovida?.apply {
                    serieList.remove(this)
                }
            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                //Nao se aplica (mover um filho )
            }
            override fun onCancelled(error: DatabaseError) {
                // Nao se aplica
            }
        })
    }
    override fun criarSerie(serie: Serie): Long {
        criarOuAtualizarSerie(serie)
        return 0L
    }
    override fun recuperarSeries(): MutableList<Serie> = serieList

    override fun removerSerie(nome: String): Int {
        seriesRTDB.child(nome).removeValue()
        return 1
    }
    private fun criarOuAtualizarSerie(serie: Serie){
        seriesRTDB.child(serie.nome).setValue(serie)
    }
}