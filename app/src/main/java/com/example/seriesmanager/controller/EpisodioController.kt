package com.example.seriesmanager.controller


import com.example.seriesmanager.dao.EpisodioDao
import com.example.seriesmanager.model.Episodio
import com.example.seriesmanager.model.EpisodioFirebase
import com.example.seriesmanager.model.Temporada

class EpisodioController(temporada: Temporada) {
    private val episodioDAO: EpisodioDao = EpisodioFirebase(temporada)

    fun inserirEpisodio(episodio: Episodio) = episodioDAO.criarEpisodio(episodio)
    fun buscarEpisodios() = episodioDAO.recuperarEpisodios()
    fun modificarEpisodio(episodio: Episodio) = episodioDAO.atualizarEpisodio(episodio)
    fun apagarEpisodio(episodioNome: String, numeroSequencial: Int) = episodioDAO.removerEpisodio(episodioNome, numeroSequencial)
}