package com.example.seriesmanager.dao
/*
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.seriesmanager.model.Episodio
/*
class EpisodioSQLite(contexto: Context): EpisodioDao {
    private val bdSeries: SQLiteDatabase = DbBuilder(contexto).getSeriesBD()

    override fun criarEpisodio(episodio: Episodio): Long {
        val episodioCv: ContentValues = converterEpisodioParaContetValues(episodio)
        return bdSeries.insert("episodio", null, episodioCv)
    }

    override fun recuperarEpisodios(temporadaId: Int): MutableList<Episodio> {
        val episodios: MutableList<Episodio> = ArrayList()
        val episodioCursor = bdSeries.rawQuery("SELECT * FROM episodio WHERE temporada_id = ?", arrayOf(temporadaId.toString()))

        if (episodioCursor.moveToFirst()) {
            while (!episodioCursor.isAfterLast) {
                val episodio: Episodio = Episodio(
                        episodioCursor.getInt(episodioCursor.getColumnIndexOrThrow("numero_sequencial")),
                        episodioCursor.getString(episodioCursor.getColumnIndexOrThrow("nome")),
                        episodioCursor.getInt(episodioCursor.getColumnIndexOrThrow("duracao")),
                        intToBoolean(episodioCursor.getInt(episodioCursor.getColumnIndexOrThrow("foi_visto"))),
                        episodioCursor.getInt(episodioCursor.getColumnIndexOrThrow("temporada_id"))
                )
                episodios.add(episodio)
                episodioCursor.moveToNext()
            }
        }
        return episodios
    }

    override fun recuperarEpisodio(numeroSequencial: Int, temporadaId: Int): Episodio? {
        var episodio: Episodio? = null
        val episodioCursor = bdSeries.rawQuery("SELECT * FROM episodio WHERE numero_sequencial = ? AND temporada_id = ?",
                arrayOf(numeroSequencial.toString(), temporadaId.toString()))
        if (episodioCursor.moveToFirst()) run {
            episodio = Episodio(
                    episodioCursor.getInt(episodioCursor.getColumnIndexOrThrow("numero_sequencial")),
                    episodioCursor.getString(episodioCursor.getColumnIndexOrThrow("nome")),
                    episodioCursor.getInt(episodioCursor.getColumnIndexOrThrow("duracao")),
                    intToBoolean(episodioCursor.getInt(episodioCursor.getColumnIndexOrThrow("foi_visto"))),
                    episodioCursor.getInt(episodioCursor.getColumnIndexOrThrow("temporada_id"))
            )
        }
        return episodio
    }


    override fun atualizarEpisodio(episodio: Episodio): Int {
        val episodioCv: ContentValues = converterEpisodioParaContetValues(episodio)
        return bdSeries.update("episodio", episodioCv, "numero_sequencial = ? AND temporada_id = ?",
                arrayOf(episodio.numeroSequencial.toString(), episodio.temporadaId.toString()))
    }

    override fun removerEpisodio(temporadaId: Int, numeroSequencial: Int): Int {
        return bdSeries.delete("episodio", "temporada_id = ? AND numero_sequencial = ?",
                arrayOf(temporadaId.toString(), numeroSequencial.toString()))
    }

    private fun converterEpisodioParaContetValues(episodio: Episodio): ContentValues {
        val episodioCv: ContentValues = ContentValues()
        episodioCv.put("numero_sequencial", episodio.numeroSequencial)
        episodioCv.put("nome", episodio.nome)
        episodioCv.put("duracao", episodio.duracao)
        episodioCv.put("foi_visto", episodio.foiVisto)
        episodioCv.put("temporada_id", episodio.temporadaId)
        return episodioCv
    }

    private fun intToBoolean(int: Int): Boolean {
        return int != 0
    }
}
*/