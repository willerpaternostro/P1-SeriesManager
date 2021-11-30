package com.example.seriesmanager.model

import com.example.seriesmanager.MainTemporadaActivity
import com.example.seriesmanager.dao.TemporadaDao
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class TemporadaFirebase: TemporadaDao {
    companion object {
        private val BD_SERIES_MANAGER = "temporadas"
    }
    private val temporadaRTDB = Firebase.database.getReference(BD_SERIES_MANAGER)
    private val temporadaList: MutableList<Temporada> = mutableListOf()

    init {
        temporadaRTDB.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val novaTemporada: Temporada? = snapshot.value as? Temporada
                novaTemporada?.apply {
                    if (temporadaList.find { it.nomeSerie == this.nomeSerie } == null) {
                        temporadaList.add(this)
                    }
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val temporadaEditada: Temporada? = snapshot.value as? Temporada
                temporadaEditada?.apply {
                    temporadaList[temporadaList.indexOfFirst { it.nomeSerie == this.nomeSerie }] =
                        this
                }
            }
            override fun onChildRemoved(snapshot: DataSnapshot) {
                val temporadaRemovida: Temporada? = snapshot.value as? Temporada
                temporadaRemovida?.apply {
                    temporadaList.remove(this)
                }
            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Não se aplica
            }
            override fun onCancelled(error: DatabaseError) {
                // Não se aplica
            }
        })
        temporadaRTDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                temporadaList.clear()
                snapshot.children.forEach {
                    val temporada: Temporada = it.getValue<Temporada>() ?: Temporada()
                    temporadaList.add(temporada)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                //Não se aplica
            }
        })
    }

    override fun criarTemporada(temporada: Temporada): Long {
        criarOuAtualizarTemporada(temporada)
        return 0L
    }

    override fun recuperarTemporadas(nomeSerie: String): MutableList<Temporada> = temporadaList

    override fun removerTemporada(nomeSerie: String, numeroSequencial: Int): Int {
        temporadaRTDB.child(nomeSerie + numeroSequencial).removeValue()
        return 1
    }

    override fun buscarTemporadaId(nomeSerie: String, numeroSequencial: Int): Int {
        //Obriga a ter um retorno
        return -1
    }

    private fun criarOuAtualizarTemporada(temporada: Temporada) {
        val noTemporada: String = temporada.nomeSerie + temporada.numeroSequencial
        temporadaRTDB.child(noTemporada).setValue(temporada)
    }
}
