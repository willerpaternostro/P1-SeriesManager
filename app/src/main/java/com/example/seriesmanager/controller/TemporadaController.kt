package com.example.seriesmanager.controller

import com.example.seriesmanager.MainTemporadaActivity
import com.example.seriesmanager.dao.TemporadaDao
import com.example.seriesmanager.dao.TemporadaSQLite
import com.example.seriesmanager.model.Temporada
import com.example.seriesmanager.model.TemporadaFirebase

class TemporadaController(mainTemporadaActivity: MainTemporadaActivity) {
    private val temporadaDAO: TemporadaDao = TemporadaFirebase()

    fun inserirTemporada(temporada: Temporada) = temporadaDAO.criarTemporada(temporada)
    fun buscarTemporadas(nomeSerie: String) = temporadaDAO.recuperarTemporadas(nomeSerie)
    fun apagarTemporadas(nomeSerie: String, numeroSequencial: Int) = temporadaDAO.removerTemporada(nomeSerie, numeroSequencial)
    fun buscarTemporadaId(nomeSerie: String, numeroSequencial: Int) = temporadaDAO.buscarTemporadaId(nomeSerie, numeroSequencial)

}