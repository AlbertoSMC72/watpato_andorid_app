package com.example.watpato.features.home.domain

import com.example.watpato.features.home.data.model.Book
import com.example.watpato.features.home.data.repository.BooksRepository

class GetAllBooksUseCase {
    private val bookRepository = BooksRepository()

    suspend operator fun invoke(): Result<List<Book>> {
        return bookRepository.getAllBooks()
    }
}