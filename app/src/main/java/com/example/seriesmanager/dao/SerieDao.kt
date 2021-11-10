package com.example.seriesmanager.dao

import com.example.seriesmanager.model.Serie

interface SerieDao {
    fun criarSerie(serie: Serie): Long
    fun recuperarSeries(): MutableList<Serie>
    fun removerSerie(nome: String): Int
}