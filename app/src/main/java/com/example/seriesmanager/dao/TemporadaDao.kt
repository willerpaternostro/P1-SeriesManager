package com.example.seriesmanager.dao

import com.example.seriesmanager.model.Temporada

interface TemporadaDao {
    fun criarTemporada(temporada: Temporada): Long
    fun recuperarTemporadas(nomeSerie: String): MutableList<Temporada>
    fun removerTemporada(nomeSerie: String, numeroSequencial: Int): Int
    fun buscarTemporadaId(nomeSerie: String, numeroSequencial: Int): Int
}