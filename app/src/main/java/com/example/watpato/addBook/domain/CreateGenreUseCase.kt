package com.example.watpato.addBook.domain

import com.example.watpato.addBook.data.model.GenreDTO
import com.example.watpato.addBook.data.repository.CreateBookRepository

class CreateGenreUseCase(
    private val repository: CreateBookRepository = CreateBookRepository()
) {
    suspend operator fun invoke(genreName: String): Result<GenreDTO> {
        return repository.createGenre(genreName)
    }
}