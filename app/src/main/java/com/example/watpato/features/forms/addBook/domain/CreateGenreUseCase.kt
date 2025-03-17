package com.example.watpato.features.forms.addBook.domain

import com.example.watpato.features.forms.addBook.data.model.GenreDTO
import com.example.watpato.features.forms.addBook.data.repository.CreateBookRepository

class CreateGenreUseCase(
    private val repository: CreateBookRepository = CreateBookRepository()
) {
    suspend operator fun invoke(genreName: String): Result<GenreDTO> {
        return repository.createGenre(genreName)
    }
}