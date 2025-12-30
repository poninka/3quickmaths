package com.example.quickmaths3.data

data class Question(
    val id: String,
    val formulaId: String,
    val questionText: String,
    val options: List<String>,
    val correctIndex: Int,
    val formulaHint: String
)
