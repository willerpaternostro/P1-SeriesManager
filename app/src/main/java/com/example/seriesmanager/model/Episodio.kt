package com.example.seriesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episodio(
        val numeroSequencial: Int =-1,
        val nome: String = "",
        val duracao: Int = -1,
        val foiVisto: Boolean = false ,
        val temporadaId: Int = -1
): Parcelable