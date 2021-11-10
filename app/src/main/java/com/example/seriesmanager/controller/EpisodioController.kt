package com.example.seriesmanager.controller

import com.example.seriesmanager.MainEpisodioActivity
import com.example.seriesmanager.dao.EpisodioDao
import com.example.seriesmanager.dao.EpisodioSQLite
import com.example.seriesmanager.model.Episodio

class EpisodioController(mainEpisodioActivity: MainEpisodioActivity) {
    private val episodioDAO: EpisodioDao = EpisodioSQLite(mainEpisodioActivity)

    fun inserirEpisodio(episodio: Episodio) = episodioDAO.criarEpisodio(episodio)
    fun buscarEpisodios(temporadaId: Int) = episodioDAO.recuperarEpisodios(temporadaId)
    fun modificarEpisodio(episodio: Episodio) = episodioDAO.atualizarEpisodio(episodio)
    fun apagarEpisodio(temporadaId: Int, numeroSequencial: Int) = episodioDAO.removerEpisodio(temporadaId, numeroSequencial)
}