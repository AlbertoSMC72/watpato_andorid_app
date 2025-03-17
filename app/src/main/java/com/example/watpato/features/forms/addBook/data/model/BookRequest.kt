package com.example.watpato.features.forms.addBook.data.model

data class BookRequest(
    val title: String,
    val description: String,
    val authorId: Int,
    val genreIds: List<Int>
)