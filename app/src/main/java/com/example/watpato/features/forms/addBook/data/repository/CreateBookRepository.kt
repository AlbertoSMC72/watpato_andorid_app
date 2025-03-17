package com.example.watpato.features.forms.addBook.data.repository

import com.example.watpato.features.forms.addBook.data.datasource.CreateBookService
import com.example.watpato.features.forms.addBook.data.model.BookRequest
import com.example.watpato.features.forms.addBook.data.model.GenreDTO
import com.example.watpato.core.network.RetrofitHelper

class CreateBookRepository {

    private val service = RetrofitHelper.createService(CreateBookService::class.java)

    suspend fun getAllGenres(): Result<List<GenreDTO>> {
        return try {
            val response = service.getAllGenres()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createGenre(name: String): Result<GenreDTO> {
        return try {
            val body = mapOf("name" to name)
            val response = service.createGenre(body)
            if (response.isSuccessful && response.body() != null) {
                val list = response.body()!!
                if (list.isNotEmpty()) {
                    val insertedId = list[0].inserted_id
                    val newGenre = GenreDTO(id = insertedId, name = name)
                    Result.success(newGenre)
                } else {
                    Result.failure(Exception("Lista de inserted_id vacía."))
                }
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun createBook(bookRequest: BookRequest): Result<Unit> {
        return try {
            val response = service.createBook(bookRequest)
            if (response.isSuccessful) {
                // El backend devuelve 200 o 201, sin cuerpo relevante
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}