package com.example.watpato.features.forms.addBook.domain

import com.example.watpato.features.forms.addBook.data.model.BookRequest
import com.example.watpato.features.forms.addBook.data.repository.CreateBookRepository

class CreateBookUseCase(
    private val repository: CreateBookRepository = CreateBookRepository()
) {
    suspend operator fun invoke(bookRequest: BookRequest): Result<Unit> {
        return repository.createBook(bookRequest)
    }
}