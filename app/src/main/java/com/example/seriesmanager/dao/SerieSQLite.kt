package com.example.seriesmanager.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.seriesmanager.model.Serie

class SerieSQLite(contexto: Context): SerieDao {
    private val bdSeries: SQLiteDatabase = DbBuilder(contexto).getSeriesBD()

    override fun criarSerie(serie: Serie): Long {
        val serieCv: ContentValues = converterSerieParaContetValues(serie)
        return bdSeries.insert("serie", null, serieCv)
    }

    override fun recuperarSeries(): MutableList<Serie> {
        val series: MutableList<Serie> = ArrayList()
        val serieCursor = bdSeries.rawQuery("SELECT * FROM serie", null)

        if (serieCursor.moveToFirst()) {
            while (!serieCursor.isAfterLast) {
                val serie: Serie = Serie(
                        serieCursor.getString(serieCursor.getColumnIndexOrThrow("nome")),
                        serieCursor.getString(serieCursor.getColumnIndexOrThrow("ano_lancamento")),
                        serieCursor.getString(serieCursor.getColumnIndexOrThrow("emissora")),
                        serieCursor.getString(serieCursor.getColumnIndexOrThrow("genero")),
                )
                series.add(serie)
                serieCursor.moveToNext()
            }
        }
        return series
    }

    override fun removerSerie(nome: String): Int {
        bdSeries.execSQL("PRAGMA foreign_keys = ON")
        return bdSeries.delete("serie", "nome = ?", arrayOf(nome))
    }

    private fun converterSerieParaContetValues(serie: Serie): ContentValues {
        val serieCv: ContentValues = ContentValues()
        serieCv.put("nome", serie.nome)
        serieCv.put("ano_lancamento", serie.anoLancamento)
        serieCv.put("emissora", serie.emissora)
        serieCv.put("genero", serie.genero)
        return serieCv
    }
}