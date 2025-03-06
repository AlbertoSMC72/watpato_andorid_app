package com.example.watpato.addBook.domain

import com.example.watpato.addBook.data.model.GenreDTO
import com.example.watpato.addBook.data.repository.CreateBookRepository

class GetAllGenresUseCase(
    private val repository: CreateBookRepository = CreateBookRepository()
) {
    suspend operator fun invoke(): Result<List<GenreDTO>> {
        return repository.getAllGenres()
    }
}