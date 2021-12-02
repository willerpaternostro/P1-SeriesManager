package com.example.seriesmanager.dao

import com.example.seriesmanager.model.Episodio

interface EpisodioDao {
    fun criarEpisodio(episodio: Episodio): Long
    fun recuperarEpisodios(): MutableList<Episodio>
    fun recuperarEpisodio(numeroSequencial: Int, temporadaId: Int): Episodio?
    fun atualizarEpisodio(episodio: Episodio): Int
    fun removerEpisodio(episodioNome: String, numeroSequencial: Int): Int
}