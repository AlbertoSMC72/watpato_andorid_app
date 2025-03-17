package com.example.watpato.features.forms.addBook.domain

import com.example.watpato.features.forms.addBook.data.model.GenreDTO
import com.example.watpato.features.forms.addBook.data.repository.CreateBookRepository

class GetAllGenresUseCase(
    private val repository: CreateBookRepository = CreateBookRepository()
) {
    suspend operator fun invoke(): Result<List<GenreDTO>> {
        return repository.getAllGenres()
    }
}