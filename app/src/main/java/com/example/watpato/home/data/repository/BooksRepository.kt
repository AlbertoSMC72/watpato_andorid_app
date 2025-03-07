package com.example.watpato.home.data.repository

import com.example.watpato.home.data.datasource.GetAllBooks
import com.example.watpato.home.data.model.Book
import com.example.watpato.core.network.RetrofitHelper

class BooksRepository {
    private val bookService = RetrofitHelper.createService(GetAllBooks::class.java)

    suspend fun getAllBooks(): Result<List<Book>> {
        return try {
            val response = bookService.getAllBooks()
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) } ?: Result.failure(Exception("No se encontraron libros"))
            } else {
                Result.failure(Exception("Error al obtener libros"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}