package com.example.watpato.home.domain

import com.example.watpato.home.data.model.Book
import com.example.watpato.home.data.repository.BooksRepository

class GetAllBooksUseCase {
    private val bookRepository = BooksRepository()

    suspend operator fun invoke(): Result<List<Book>> {
        return bookRepository.getAllBooks()
    }
}