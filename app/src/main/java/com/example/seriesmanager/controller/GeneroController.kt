package com.example.seriesmanager.controller

import com.example.seriesmanager.SerieActivity
import com.example.seriesmanager.dao.GeneroDao
import com.example.seriesmanager.dao.GeneroSQLite

class GeneroController(serieActivity: SerieActivity) {
    private val generoDAO: GeneroDao = GeneroSQLite(serieActivity)
    fun buscarGeneros() = generoDAO.recuperarGeneros()
}