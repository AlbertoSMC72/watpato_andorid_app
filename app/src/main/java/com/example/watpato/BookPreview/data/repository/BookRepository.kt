package com.example.watpato.BookPreview.data.repository

import com.example.watpato.BookPreview.data.datasource.GetBook
import com.example.watpato.BookPreview.data.model.entities.Book
import com.example.watpato.BookPreview.data.model.requests.BookSubscriptionRequest
import com.example.watpato.core.network.RetrofitHelper

class BookRepository {
    private val bookService = RetrofitHelper.createService(GetBook::class.java)

    suspend fun getBookById(id: Int): Result<Book> {
        return try {
            val response = bookService.getBookById(id)
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) } ?: Result.failure(Exception("Libro no encontrado"))
            } else {
                Result.failure(Exception("Error al obtener el libro"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun subscribeToBook(userId: Int, bookId: Int): Result<Unit> {
        return try {
            val request = BookSubscriptionRequest(userId, bookId)
            bookService.subscribeToBook(request)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun unsubscribeToBook(userId: Int, bookId: Int): Result<Unit> {
        return try {
            val request = BookSubscriptionRequest(userId, bookId)
            bookService.unsubscribeToBook(request)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}