package com.example.watpato.addBook.domain

import com.example.watpato.addBook.data.model.BookRequest
import com.example.watpato.addBook.data.repository.CreateBookRepository

class CreateBookUseCase(
    private val repository: CreateBookRepository = CreateBookRepository()
) {
    suspend operator fun invoke(bookRequest: BookRequest): Result<Unit> {
        return repository.createBook(bookRequest)
    }
}