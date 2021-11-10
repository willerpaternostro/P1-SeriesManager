package com.example.seriesmanager.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase

class GeneroSQLite(contexto: Context): GeneroDao {
    private val bdSeries: SQLiteDatabase = DbBuilder(contexto).getSeriesBD()


    override fun recuperarGeneros(): MutableList<String> {
        val generos: MutableList<String> = ArrayList()
        val generoCursor = bdSeries.rawQuery("SELECT nome FROM genero", null)

        if(generoCursor.moveToFirst()) {
            while (!generoCursor.isAfterLast) {
                val genero: String = generoCursor.getString(generoCursor.getColumnIndexOrThrow("nome"))
                generos.add(genero)
                generoCursor.moveToNext()
            }
        }
        return generos
    }
}