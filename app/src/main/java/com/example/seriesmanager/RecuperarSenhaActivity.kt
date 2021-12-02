package com.example.seriesmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.seriesmanager.databinding.ActivityRecuperarSenhaBinding

class RecuperarSenhaActivity : AppCompatActivity() {
    private val activityRecuperarSenhaBinding: ActivityRecuperarSenhaBinding by lazy {
        ActivityRecuperarSenhaBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityRecuperarSenhaBinding.root)
        supportActionBar?.subtitle = "Recuperar senha"
    }
}