package com.example.seriesmanager

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.seriesmanager.databinding.ActivitySerieBinding
import com.example.seriesmanager.model.Serie


class SerieActivity: AppCompatActivity() {
    private val activitySerieBinding: ActivitySerieBinding by lazy {
        ActivitySerieBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activitySerieBinding.root)

        activitySerieBinding.salvarSerieBt.setOnClickListener{ view:View  ->
            val serie = Serie(
                    activitySerieBinding.nomeSerieTv.text.toString(),
                    activitySerieBinding.anoLancamentoTv.text.toString(),
                    activitySerieBinding.emissoraTv.text.toString(),
                    activitySerieBinding.generoTv.text.toString()
            )
           val resultadoSerieIntent = Intent(this, MainActivity::class.java)
            resultadoSerieIntent.putExtra(MainActivity.EXTRA_SERIE,serie)
            setResult(RESULT_OK,resultadoSerieIntent)
            finish()
        }
    }
}