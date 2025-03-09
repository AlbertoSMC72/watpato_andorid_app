package com.example.watpato.BookPreview.domain

import com.example.watpato.BookPreview.data.model.entities.Book
import com.example.watpato.BookPreview.data.repository.BookRepository

class BookPreviewUseCase {
    private val bookRepository = BookRepository()

    suspend operator fun invoke(bookId: Int): Result<Book> {
        return bookRepository.getBookById(bookId)
    }

    suspend fun subscribe(userId: Int, bookId: Int): Result<Unit> {
        return bookRepository.subscribeToBook(userId, bookId)
    }

    suspend fun unsubscribe(userId: Int, bookId: Int): Result<Unit> {
        return bookRepository.unsubscribeToBook(userId, bookId)
    }

    suspend fun isSubscribed(userId: Int, bookId: Int): Result<Boolean> {
        return bookRepository.isSubscribedToBook(userId, bookId)
    }
}