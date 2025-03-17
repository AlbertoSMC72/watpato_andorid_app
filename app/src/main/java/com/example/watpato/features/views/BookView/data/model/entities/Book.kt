package com.example.watpato.features.views.BookView.data.model.entities

data class Book (
    val id: Int,
    val title: String,
    val description: String,
    val created_at: String,
    val author_id: Int,
    val author_name: String,
    val genres: List<String>,
    val chapters: List<Chapter>
)

