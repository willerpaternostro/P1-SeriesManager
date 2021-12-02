package com.example.seriesmanager

import com.google.firebase.auth.FirebaseAuth

// Objeto único para toda aplicação (Singleton)
object AutenticacaoFirebase {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
}