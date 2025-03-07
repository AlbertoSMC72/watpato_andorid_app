package com.example.watpato.BookPreview.data.model.entities

data class Book (
    val id: Int,
    val title: String,
    val description: String,
    val genres: List<String>,
    val chapters: List<Chapter>
)

