package com.example.seriesmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.seriesmanager.databinding.ActivityMainBinding
import com.example.seriesmanager.model.Serie

class MainActivity : AppCompatActivity() {
    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val seriesList: MutableList<Serie> = mutableListOf()

    //ADAPTER
    private val seriesAdapter: ArrayAdapter<String> by lazy{
        val seriesStringList = mutableListOf<String>()
        seriesList.forEach { serie -> seriesStringList.add(serie.toString())}
        ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, seriesStringList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        inicializarSeriesList()
        activityMainBinding.listaSerieslv.adapter = seriesAdapter

    }



    private fun inicializarSeriesList(){
        for(indice in 1..10){
            seriesList.add(
                Serie(
                    "${indice}",
                     "${indice}",
                    "${indice}",
                    "${indice}"
                )
            )
        }
    }
}