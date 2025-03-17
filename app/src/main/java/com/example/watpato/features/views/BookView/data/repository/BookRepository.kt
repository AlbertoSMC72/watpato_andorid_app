package com.example.watpato.features.views.BookView.data.repository

import com.example.watpato.features.views.BookView.data.datasource.GetBook
import com.example.watpato.features.views.BookView.data.model.entities.Book
import com.example.watpato.features.views.BookView.data.model.requests.BookSubscriptionRequest
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

    suspend fun isSubscribedToBook(userId: Int, bookId: Int): Result<Boolean> {
        return try {
            val response = bookService.isSubscribedToBook(userId, bookId)
            if (response.isSuccessful) {
                val subscriptionResponse = response.body()
                Result.success(subscriptionResponse?.isSubscribed ?: false)
            } else {
                Result.failure(Exception("Error al verificar suscripción"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}