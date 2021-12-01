package com.example.seriesmanager.model

import com.example.seriesmanager.dao.EpisodioDao
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class EpisodioFirebase(temporada:Temporada): EpisodioDao {
    companion object {
        private val BD_SERIES_MANAGER = "episodios"
    }
    private val episodioRTDB = Firebase.database.getReference(temporada.nomeSerie + temporada.numeroSequencial)
    private val episodioList: MutableList<Episodio> = mutableListOf()

    init{
        episodioRTDB.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val novoEpisodio: Episodio? = snapshot.value as? Episodio
                novoEpisodio?.apply {
                    if (episodioList.find { it.nome == this.nome } == null) {
                        episodioList.add(this)
                    }
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val episodioEditado: Episodio? = snapshot.value as? Episodio
                episodioEditado?.apply {
                    episodioList[episodioList.indexOfFirst { it.nome == this.nome }] = this
                }
            }
            override fun onChildRemoved(snapshot: DataSnapshot) {
                val episodioExcluido: Episodio? = snapshot.value as? Episodio
                episodioExcluido?.apply {
                    episodioList.remove(this)
                }
            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Não se aplica
            }
            override fun onCancelled(error: DatabaseError) {
                // Não se aplica
            }
        })
        episodioRTDB.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                episodioList.clear()
                snapshot.children.forEach {
                    val episodio: Episodio = it.getValue<Episodio>()?: Episodio()
                    episodioList.add(episodio)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                //Não se aplica
            }
        })
    }
    override fun criarEpisodio(episodio: Episodio): Long {
        criarOuAtualizarEpisodio(episodio)
        return 0L
    }
    override fun recuperarEpisodios(temporadaId: Int): MutableList<Episodio> = episodioList
    override fun recuperarEpisodio(numeroSequencial: Int, temporadaId: Int): Episodio? {
        return null
    }
    override fun atualizarEpisodio(episodio: Episodio): Int {
        criarOuAtualizarEpisodio(episodio)
        return 1
    }
    override fun removerEpisodio(temporadaId: Int, numeroSequencial: Int): Int {
        return 1
    }

    private fun criarOuAtualizarEpisodio(episodio: Episodio) {
        val noEpisodio: String = episodio.numeroSequencial.toString() + " - " + episodio.nome
        episodioRTDB.child(noEpisodio).setValue(episodio)
    }
}