package com.example.seriesmanager.dao

interface GeneroDao{
    fun recuperarGeneros(): MutableList<String>
}